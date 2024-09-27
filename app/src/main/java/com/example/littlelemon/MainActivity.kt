package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = this.getSharedPreferences(getString(R.string.prefs_title), Context.MODE_PRIVATE)
        val startDestination = sharedPrefs?.getString("destination", Onboarding.route) ?: Onboarding.route
        setContent {
            val navController = rememberNavController()

            Navigation(navController, startDestination)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val navController = rememberNavController()

    Navigation(navController, Onboarding.route)
}