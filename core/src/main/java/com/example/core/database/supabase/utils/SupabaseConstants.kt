package com.example.core.database.supabase.utils

object SupabaseConstants {
    /**
     * Responses from Supabase
     */
    const val INVALID_CREDENTIALS = "invalid_credentials"
    const val USER_NOT_FOUND = "user_not_found"
    const val EMAIL_NOT_CONFIRMED = "email_not_confirmed"
    const val TIMEOUT = "timeout"
    const val UNKNOWN = "unknown"
    const val NO_INTERNET = "no_internet"
    const val VALIDATION_FAILED = "validation_failed"
    const val ERROR_NOT_ADDED_YET = "error_not_added_yet"

    /**
     * Responses to the User
     */
    const val INVALID_CREDENTIALS_RESPONSE = "Invalid email or password."
    const val INVALID_EMAIL_RESPONSE = "Invalid email"
    const val INVALID_REGISTER_RESPONSE = "Invalid email, username or password"
    const val USER_NOT_FOUND_RESPONSE = "User does not exist."
    const val EMAIL_NOT_CONFIRMED_RESPONSE = "Please confirm your email before logging in."
    const val UNKNOWN_ERROR_RESPONSE = "An unknown error occurred"
    const val TIMEOUT_ERROR_RESPONSE = "Request timed out. Check your internet connection."
    const val NO_INTERNET_RESPONSE = "No internet connection. Please try again."
}