package com.example.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ui.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    val logoRes = if (isSystemInDarkTheme()) {
        R.drawable.logo // Replace with your dark mode logo
    } else {
        R.drawable.logo_white // Replace with your light mode logo
    }

    Image(
        painter = painterResource(id = logoRes),
        contentDescription = "App Logo",
        modifier = modifier
    )
}