package com.jumpingphantom.flow

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.jumpingphantom.flow.core.ui.MainViewModel
import com.jumpingphantom.flow.core.ui.theme.FlowTheme
import com.jumpingphantom.flow.navigation.MainNavHost
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            FlowTheme {
                val mainViewModel = getViewModel<MainViewModel>()
                MainNavHost()
            }
        }
    }
}