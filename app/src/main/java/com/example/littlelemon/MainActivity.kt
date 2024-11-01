package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

private val client = HttpClient(Android) {
    install(ContentNegotiation) {
        json(contentType = ContentType("text", "plain"))
    }
}

private val menuItemsLiveData = MutableLiveData<List<MenuItemNetwork>>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = this.getSharedPreferences(getString(R.string.prefs_title), Context.MODE_PRIVATE)
        val startDestination = sharedPrefs?.getString("destination", Onboarding.route) ?: Onboarding.route

        lifecycleScope.launch {
            val menuItems = getMenu()

            runOnUiThread {
                menuItemsLiveData.value = menuItems
            }
        }

        setContent {
            val navController = rememberNavController()

            Navigation(navController, startDestination)
        }
    }

    private suspend fun getMenu(): List<MenuItemNetwork> {
        val response: MenuNetworkdata =
            client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                .body()

        return response.menu
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val navController = rememberNavController()

    Navigation(navController, Onboarding.route)
}