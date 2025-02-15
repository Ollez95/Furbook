package com.example.authentication.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterText(navigateToRegister: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        append("Don't have an account? ")

        withLink(
            link = LinkAnnotation.Clickable(
                tag = "TAG",
                linkInteractionListener = {
                    // handle onClick here
                    Log.d("LoginScreen - RegisterText", "Clicked")
                    navigateToRegister()
                },
            ),
        ) {
            withStyle(
                style = SpanStyle( // ✅ Use SpanStyle instead of typography
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append("Register")
            }
        }
    }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier.padding(top = 8.dp)
    )
}