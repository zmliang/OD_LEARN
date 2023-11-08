package com.pos.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColorScheme(
    primary = Color.Blue,
    secondary = Color.Blue
)

private val LightColorPalette = lightColorScheme(
    primary = Color.Blue,
    secondary = Color.Blue
)



@Composable
fun MviTheme(darkTheme: Boolean = isSystemInDarkTheme(),content: @Composable ()->Unit){
    val color = if (darkTheme){
        DarkColorPalette
    }else{
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = color,
        typography = MTypography,
        shapes = Shapes,
        content = content
    )
}