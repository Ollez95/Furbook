package com.example.home.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.authentication.login.usecase.LogoutAccountUseCase
import com.example.core.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutAccountUseCase: LogoutAccountUseCase
): ViewModel()
{
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    private val _eventFlow = MutableSharedFlow<MainEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.Logout -> logout()
            else -> { /* Ignore */
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutAccountUseCase.invoke().collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Response.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(MainEvent.LogoutSuccess)  // Correct success event
                    }

                    is Response.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(MainEvent.LogoutError)
                    }
                }
            }
        }
    }
}