package com.example.authentication.ui.recover_password

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.authentication.recover_password.usecase.RecoverPasswordUseCase
import com.example.core.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverPasswordViewModel @Inject constructor(
    private val getRecoverPasswordUseCase: RecoverPasswordUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val emailKey = "email"
    val email = savedStateHandle[emailKey] ?: ""

    private val _state = MutableStateFlow(RecoverPasswordState())
    val state: StateFlow<RecoverPasswordState> = _state
        .onStart { initializeData() } // ✅ Ensure state updates with email
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecoverPasswordState(email = email) // ✅ Initial Value
        )

    private val _eventFlow = MutableSharedFlow<RecoverPasswordEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RecoverPasswordEvent) {
        when (event) {
            is RecoverPasswordEvent.EmailChanged -> _state.update { it.copy(email = event.email) }
            RecoverPasswordEvent.RecoverPassword -> recoverPassword(state.value.email)
            RecoverPasswordEvent.NavigateToLogin -> viewModelScope.launch { _eventFlow.emit(RecoverPasswordEvent.NavigateToLogin) }
        }
    }

    private fun initializeData() {
        Log.d("RecoverPasswordViewModel", "initializeData: $email")
        _state.update { it.copy(email = email) }
    }

    private fun recoverPassword(email: String) {
        viewModelScope.launch {
            getRecoverPasswordUseCase.invoke(email).collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Response.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(RecoverPasswordEvent.RecoverPasswordSuccess)  // Correct success event
                    }

                    is Response.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(RecoverPasswordEvent.RecoverPasswordError(response.message))
                    }
                }
            }
        }
    }
}




