package com.example.home.ui.main

interface MainEvent {
    data object Logout : MainEvent
    data object LogoutSuccess : MainEvent
    data object OpenCloseDrawer: MainEvent
    data class NavigateToBottomSheetScreens(val mainScreenEnum: MainScreenEnum): MainEvent
    data object NavigateToPetAddPost : MainEvent
}