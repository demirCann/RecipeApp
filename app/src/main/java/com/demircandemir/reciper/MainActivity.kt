 package com.demircandemir.reciper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.demircandemir.reciper.navigation.RecipeNavigationGraph
import com.demircandemir.reciper.ui.theme.RecipeFinderTheme
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeFinderTheme {
                RecipeNavigationGraph(modifier = Modifier.fillMaxSize())
            }
        }
    }
}