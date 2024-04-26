package com.sixkids.navigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.navigator.testscreens.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UlbanTheme {
               HomeScreen()
            }
        }
    }
}