package com.example.home.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.authentication.login.usecase.LogoutAccountUseCase
import com.example.core.domain.authentication.repository.UserRepository
import com.example.core.domain.authentication.shared.GetCurrentUserIdUseCase
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
class MainViewModel @Inject constructor(
    private val logoutAccountUseCase: LogoutAccountUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.onStart { loadData() }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = MainState()
        )

    private val _eventFlow = MutableSharedFlow<MainEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.Logout -> logout()
            is MainEvent.OpenCloseDrawer -> viewModelScope.launch { _eventFlow.emit(MainEvent.OpenCloseDrawer) }
            is MainEvent.NavigateToBottomSheetScreens -> viewModelScope.launch { _state.update { it.copy(screen = event.mainScreenEnum) } }

            is MainEvent.NavigateToPetAddPost -> {
                viewModelScope.launch {
                    _eventFlow.emit(MainEvent.NavigateToPetAddPost)
                }
            }

            else -> { /* Ignore */
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val idResponse = getCurrentUserIdUseCase.invoke()
            val id = if (idResponse is Response.Success) {
                idResponse.data
            } else {
                ""
            }
            val user = userRepository.getUserById(id)
            if (user is Response.Success) {
                _state.update { it.copy(user = user.data) }
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

                    is Response.Success, is Response.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(MainEvent.LogoutSuccess)  // Correct success event
                    }
                }
            }
        }
    }
}