package com.example.home.ui.main

interface MainEvent {
    data object Logout : MainEvent
    data object LogoutSuccess : MainEvent
    data object LogoutError: MainEvent
    data object NavigateToHome: MainEvent
    data object NavigateToChat: MainEvent
    data object NavigateToInbox: MainEvent
}