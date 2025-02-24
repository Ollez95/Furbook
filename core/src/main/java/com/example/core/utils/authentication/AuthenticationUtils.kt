package com.example.core.utils.authentication

import android.util.Patterns
import com.example.core.utils.exceptions.NotValidEmail
import com.example.core.utils.exceptions.NotValidPasswordOrEmail
import com.example.core.utils.exceptions.NotValidUsernameEmailOrPassword

object AuthenticationUtils {

    fun validateLoginCredentials(email: String?, password: String?) {
        require(isValidEmail(email)) { throw NotValidPasswordOrEmail() }
        require(isValidPassword(password)) { throw NotValidPasswordOrEmail() }
    }

    fun validateRecoveryCredentials(email: String?) {
        require(isValidEmail(email)) { throw NotValidEmail() }
    }

    fun validateRegisterCredentials(
        username: String?,
        email: String?,
        password: String?,
        passwordConfirmation: String?
    ) {
        require(isValidUsername(username)) { throw NotValidUsernameEmailOrPassword() }
        require(isValidEmail(email)) { throw NotValidUsernameEmailOrPassword() }
        require(isValidPassword(password)) { throw NotValidUsernameEmailOrPassword() }
        require(isValidPassword(passwordConfirmation)) { throw NotValidUsernameEmailOrPassword() }
        require(password == passwordConfirmation) { throw NotValidUsernameEmailOrPassword() }
    }

    private fun isValidUsername(username: String?) = !username.isNullOrBlank() && username.length >= 3

    private fun isValidEmail(email: String?) = !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String?) = !password.isNullOrBlank() && password.length >= 6
}
