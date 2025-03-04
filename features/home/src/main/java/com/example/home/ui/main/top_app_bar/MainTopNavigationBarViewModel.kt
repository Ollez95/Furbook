package com.example.home.ui.main.top_app_bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainTopNavigationBarViewModel @Inject constructor() : ViewModel() {

    private val _eventFlow = MutableSharedFlow<MainTopNavigationBarEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: MainTopNavigationBarEvent) {
        when (event) {
            is MainTopNavigationBarEvent.OpenCloseDrawer ->
                viewModelScope.launch { _eventFlow.emit(MainTopNavigationBarEvent.OpenCloseDrawer) }
            is MainTopNavigationBarEvent.NavigateToPetAddPost -> {
                viewModelScope.launch { _eventFlow.emit(MainTopNavigationBarEvent.NavigateToPetAddPost) }
            }
        }
    }
}