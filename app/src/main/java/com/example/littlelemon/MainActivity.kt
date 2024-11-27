package com.example.littlelemon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val client = HttpClient(Android) {
    install(ContentNegotiation) {
        json(contentType = ContentType("text", "plain"))
    }
}

private val menuItemsLiveData = MutableLiveData<List<MenuItemNetwork>>()

var menuItemList: List<MenuItem> = emptyList()

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            MenuDatabase::class.java,
            "menu.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = this.getSharedPreferences(getString(R.string.prefs_title), Context.MODE_PRIVATE)
        val startDestination = sharedPrefs?.getString("destination", Onboarding.route) ?: Onboarding.route

        lifecycleScope.launch {
            val menuItems = getMenu()

            runOnUiThread {
                menuItemsLiveData.value = menuItems
            }

            withContext(IO) {
                menuItemsLiveData.value?.forEach {
                    try {
                        database.menuDao().saveMenuItem(MenuItem(it.id, it.title, it.description, it.price, it.image, it.category))
                    } catch (e: Exception) {
                        Log.e("database", e.toString())
                    }
                }
            }
        }

        setContent {
            val navController = rememberNavController()

            val menuItems by database.menuDao().getAllMenuItems()
                .observeAsState(emptyList())

            menuItemList = menuItems

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