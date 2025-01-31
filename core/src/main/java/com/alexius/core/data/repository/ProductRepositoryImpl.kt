package com.alexius.core.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.alexius.core.data.model.remote.UserInfoFirestore
import com.alexius.core.data.model.remote.toDomainModel
import com.alexius.core.data.model.remote.toProduct
import com.alexius.core.domain.model.Product
import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.core.domain.network.NetworkService
import com.alexius.core.domain.repository.ProductRepository
import com.alexius.core.util.UiState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import java.io.ByteArrayOutputStream
import java.util.UUID

class ProductRepositoryImpl(
    private val networkService: NetworkService
) : ProductRepository {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()

    override fun getProducts(): Flow<UiState<List<Product>>> = flow {
        try {
            emit(UiState.Loading)
            val products = networkService.getProducts().map { it.toProduct() }
            emit(UiState.Success(products))
        } catch (e: ClientRequestException){
            emit(UiState.Error(e.message))
        } catch (e: ServerResponseException){
            emit(UiState.Error(e.message))
        } catch (e: IOException){
            emit(UiState.Error(e.message?:"IOException"))
        } catch (e: Exception){
            emit(UiState.Error(e.message?:"Unknown Error"))
        }
    }

    override fun signInWithEmailPassword(
        email: String,
        password: String
    ): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(UiState.Success(true))
                } else {
                    trySend(UiState.Error(task.exception?.message ?: "Unknown error"))
                    Log.d(
                        "AuthenticationManager",
                        "createAccountWithEmailPassword: ${task.exception?.message}"
                    )
                }
            }
        awaitClose()
    }

    override fun createAccountWithEmailPassword(
        email: String,
        password: String
    ): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(UiState.Success(true))
                } else {
                    trySend(UiState.Error(task.exception?.message ?: "Unknown error"))
                    Log.d(
                        "AuthenticationManager",
                        "createUserWithEmailAndPass: ${task.exception?.message}"
                    )
                }
            }
        awaitClose()
    }

    override fun resetPassword(email: String): Flow<UiState<Boolean>> = callbackFlow {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(UiState.Success(true))
                } else {
                    trySend(UiState.Error(task.exception?.message ?: "Unknown error"))
                    Log.d(
                        "AuthenticationManager",
                        "resetPassword: ${task.exception?.message}"
                    )
                }
            }
        awaitClose()
    }

    override fun initUserInfoInFirestore(userInfo: UserInfoDomain): Flow<UiState<Unit>> = callbackFlow{
        val user = hashMapOf(
            "email" to userInfo.email,
            "name" to userInfo.name,
            "profileImage" to userInfo.profileImage
        )
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        trySend(UiState.Loading)
        db.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {task ->
                trySend(UiState.Success(Unit))
            }
            .addOnFailureListener(){exception ->
                trySend(UiState.Error(exception.message ?: "Unknown error"))
            }
        awaitClose()
    }

    override fun getUserInfoFromFirestore(): Flow<UiState<UserInfoDomain>> = callbackFlow {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        trySend(UiState.Loading)
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userInfo = UserInfoFirestore(
                        email = document.getString("email") ?: "",
                        name = document.getString("name") ?: "",
                        profileImage = document.getString("profileImage") ?: ""
                    )
                    trySend(UiState.Success(userInfo.toDomainModel()))
                } else {
                    trySend(UiState.Error("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                trySend(UiState.Error(exception.message ?: "Unknown error"))
            }
        awaitClose()
    }

    override fun uploadImageAndStoreReference(bitmap: Bitmap): Flow<UiState<String>> = callbackFlow {
        trySend(UiState.Loading)
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

        // Convert bitmap to byte array
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Create storage reference
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

        // Upload image
        imageRef.putBytes(data)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imageRef.downloadUrl
            }

            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val downloadUri = task.result
                    // Store reference in Firestore
                    // Update one field, creating the document if it does not already exist.
                    val imageData = hashMapOf("profileImage" to downloadUri.toString())

                    db.collection("users").document(userId)
                        .set(imageData, SetOptions.merge())
                        .addOnSuccessListener{
                            trySend(UiState.Success(downloadUri.toString()))
                            close()
                        }
                        .addOnFailureListener { e ->
                            trySend(UiState.Error(e.message ?: "Unknown error"))
                            close()
                        }
                } else{
                    trySend(UiState.Error(task.exception?.message ?: "Unknown error"))
                    close()
                }
            }
            .addOnFailureListener { e ->
                trySend(UiState.Error(e.message ?: "Unknown error"))
                close()
            }

        awaitClose()
    }
}