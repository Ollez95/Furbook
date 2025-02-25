package com.example.home.ui.main

import com.example.core.domain.home.main.model.UserInformation

data class MainState(
    val isLoading: Boolean = false,
    val userInformation: UserInformation = UserInformation(),
)