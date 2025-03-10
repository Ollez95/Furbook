package com.example.core.database.supabase.model

import com.example.core.database.supabase.utils.SupabaseConstants.EMAIL_NOT_CONFIRMED
import com.example.core.database.supabase.utils.SupabaseConstants.ERROR_NOT_ADDED_YET
import com.example.core.database.supabase.utils.SupabaseConstants.INVALID_CREDENTIALS
import com.example.core.database.supabase.utils.SupabaseConstants.INVALID_CREDENTIALS_RESPONSE
import com.example.core.database.supabase.utils.SupabaseConstants.INVALID_EMAIL_RESPONSE
import com.example.core.database.supabase.utils.SupabaseConstants.INVALID_REGISTER_RESPONSE
import com.example.core.database.supabase.utils.SupabaseConstants.NO_INTERNET
import com.example.core.database.supabase.utils.SupabaseConstants.NO_INTERNET_RESPONSE
import com.example.core.database.supabase.utils.SupabaseConstants.TIMEOUT
import com.example.core.database.supabase.utils.SupabaseConstants.TIMEOUT_ERROR_RESPONSE
import com.example.core.database.supabase.utils.SupabaseConstants.UNKNOWN
import com.example.core.database.supabase.utils.SupabaseConstants.UNKNOWN_ERROR_RESPONSE
import com.example.core.database.supabase.utils.SupabaseConstants.USER_NOT_FOUND
import com.example.core.database.supabase.utils.SupabaseConstants.VALIDATION_FAILED

sealed class AuthenticationErrorResponse(val code: String, open val message: String) {

    data class InvalidCredentials(val error: String, val description: String) : AuthenticationErrorResponse(error, description)
    data class UserNotFound(val error: String, val description: String) : AuthenticationErrorResponse(error, description)
    data class EmailNotConfirmed(val error: String, val description: String) : AuthenticationErrorResponse(error, description)
    data class ValidationFailed(val error: String, val description: String) : AuthenticationErrorResponse(error, description)
    data class ErrorNotAddedYet(val description: String) : AuthenticationErrorResponse(ERROR_NOT_ADDED_YET, description)

    data object InvalidEmailOrPassword : AuthenticationErrorResponse(INVALID_CREDENTIALS, INVALID_CREDENTIALS_RESPONSE)
    data object InvalidEmail : AuthenticationErrorResponse(INVALID_CREDENTIALS, INVALID_EMAIL_RESPONSE)
    data object InvalidUsernameEmailOrPassword : AuthenticationErrorResponse(INVALID_CREDENTIALS, INVALID_REGISTER_RESPONSE)
    data object UnknownError : AuthenticationErrorResponse(UNKNOWN, UNKNOWN_ERROR_RESPONSE)
    data object NoInternet : AuthenticationErrorResponse(NO_INTERNET, NO_INTERNET_RESPONSE)
    data object Timeout : AuthenticationErrorResponse(TIMEOUT, TIMEOUT_ERROR_RESPONSE)

    companion object {
        fun fromCode(error: String, description: String): AuthenticationErrorResponse {
            return when (error) {
                INVALID_CREDENTIALS -> InvalidCredentials(error, description)
                USER_NOT_FOUND -> UserNotFound(error, description)
                EMAIL_NOT_CONFIRMED -> EmailNotConfirmed(error, description)
                VALIDATION_FAILED -> ValidationFailed(error, description)
                else -> ErrorNotAddedYet(error)
            }
        }
    }
}