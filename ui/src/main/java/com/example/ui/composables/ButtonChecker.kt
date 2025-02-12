package com.example.ui.composables

import android.content.res.Configuration
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.FurbookTheme

@Composable
fun ButtonTest(modifier: Modifier = Modifier) {
    Button(onClick = {}) { Text("Test Button") }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES // Enables dark mode preview
)
@Composable
private fun onBoardingScreenPreview() {
    FurbookTheme(dynamicColor = false) {
        ButtonTest()
    }
}