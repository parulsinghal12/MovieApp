package com.example.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomTextView(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color,
        maxLines = maxLines
    )
}

@Composable
fun CustomTextViewBold(
    text: String,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = style.copy(color = Color.Black, fontWeight = FontWeight.Bold),
        modifier = modifier,
        maxLines = maxLines
    )
}