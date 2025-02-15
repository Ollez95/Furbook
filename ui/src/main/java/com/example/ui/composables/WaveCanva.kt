package com.example.ui.composables

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.FurbookTheme

@Composable
fun WaveBackground(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.primary) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp) // Adjust wave height
    ) {
        val path = Path().apply {
            moveTo(0f, size.height * 0.7f) // Start near the bottom left
            quadraticTo(
                size.width * 0.25f, size.height,  // First control point
                size.width * 0.5f, size.height * 0.85f // First curve
            )
            quadraticTo(
                size.width * 0.75f, size.height * 0.6f, // Second control point
                size.width, size.height * 0.8f // Second curve
            )
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
            close()
        }
        drawPath(path = path, color = color)
    }
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
@Preview
@Composable
private fun WaveBackgroundPreview() {
    FurbookTheme {
        WaveBackground()
    }
}