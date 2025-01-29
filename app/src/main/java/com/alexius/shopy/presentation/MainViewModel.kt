package com.alexius.shopy.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecase.ReadAppEntry
import com.alexius.core.util.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val readAppEntryUseCase: ReadAppEntry) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    init {
        readAppEntry()
    }

    fun readAppEntry() {
        viewModelScope.launch{
            readAppEntryUseCase().collect {shouldStartFromMainNavigation ->
                _state.value = _state.value.copy(startDestination = if(shouldStartFromMainNavigation) {
                    Route.MainNavigation.route
                } else {
                    Route.EntryNavigation.route
                })
                Log.d("MainViewModel", "startDestination: ${state.value.startDestination}")
                delay(300)
                _state.value = _state.value.copy(splashCondition = false)
            }
        }
    }
}