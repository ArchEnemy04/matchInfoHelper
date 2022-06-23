package com.alacrity.matchInfoHelper

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.alacrity.matchInfoHelper.theme.MusicTheme
import com.alacrity.matchInfoHelper.ui.main.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object Destinations {
    const val HOME_ROUTE = "home"
}

@Composable
fun MatchInfoHelperApp(
    context: Context,
    homeViewModel: MainViewModel
) {
    MusicTheme {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }

        MatchInfoHelperNavGraph(
                context = context,
                homeViewModel = homeViewModel,
            )
    }

}


fun customShape(screenWidth: Float, screenHeight: Float) = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f, 0f, screenWidth / 5, screenHeight))
    }
}

