@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class)

package com.example.littlelemon

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

val markaziFontFamily = FontFamily(
    Font(R.font.markazitext_regular, FontWeight.Normal)
)

const val heroTitle = "Little Lemon"
const val heroSubtitle = "Chicago"
const val heroDescription = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist."

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavHostController?) {
    val context = LocalContext.current
    val database by lazy {
        Room.databaseBuilder(
            context,
            MenuDatabase::class.java,
            "menu.db"
        ).build()
    }
    var searchPhrase by remember { mutableStateOf("") }
    var filterCategory by remember { mutableStateOf("")}
    val menuItems by database.menuDao().getAllMenuItems()
        .observeAsState(emptyList())
    var displayItems = menuItems

    Scaffold { _ ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .padding(0.dp, 0.dp, 0.dp, 80.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Little Lemon Logo",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.Center)
                )
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            onClickLabel = "Profile image button",
                            onClick = {
                                navController?.navigate(Profile.route)
                            }
                        )
                        .fillMaxHeight(0.5f)
                        .align(Alignment.CenterEnd)
                        .padding(20.dp, 0.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.primary_green))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)
                ) {
                    Text(
                        text = heroTitle,
                        fontSize = 60.sp,
                        color = colorResource(id = R.color.primary_yellow),
                        fontFamily = markaziFontFamily,
                        modifier = Modifier
                            .wrapContentHeight(Alignment.Bottom)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                    ) {
                        Text(
                            text = heroSubtitle,
                            fontSize = 36.sp,
                            color = colorResource(id = R.color.highlight_white),
                            fontFamily = markaziFontFamily,
                            modifier = Modifier
                                .padding(0.dp, 0.dp,0.dp, 10.dp)
                        )
                        Text(
                            text = heroDescription,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.highlight_white),
                            fontFamily = karlaFontFamily,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hero),
                            contentDescription = "Hero Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.primary_green))
                        .padding(20.dp, 10.dp)
                ) {
                    TextField(
                        value = searchPhrase,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            searchPhrase = it
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.example_search),
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.highlight_black),
                                fontFamily = karlaFontFamily
                            )
                        },
                        leadingIcon = { Icon( imageVector = Icons.Default.Search, contentDescription = "") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.home_delivery),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.highlight_black),
                    fontFamily = karlaFontFamily,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                Button(
                    onClick =
                    {
                        filterCategory = "starters"
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_green_faded)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .defaultMinSize(1.dp, 1.dp)
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.home_starters),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.primary_green),
                        fontFamily = karlaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
                Button(
                    onClick =
                    {
                        filterCategory = "mains"
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_green_faded)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .defaultMinSize(1.dp, 1.dp)
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.home_mains),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.primary_green),
                        fontFamily = karlaFontFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Button(
                    onClick =
                    {
                        filterCategory = "desserts"
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_green_faded)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .defaultMinSize(1.dp, 1.dp)
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.home_desserts),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.primary_green),
                        fontFamily = karlaFontFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Button(
                    onClick =
                    {
                        filterCategory = "drinks"
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_green_faded)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .defaultMinSize(1.dp, 1.dp)
                        .padding(20.dp, 0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.home_drinks),
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.primary_green),
                        fontFamily = karlaFontFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(0.dp, 10.dp)
            )
            if (searchPhrase.isNotEmpty()) {
                filterCategory = ""
                displayItems = menuItems.filter {
                    it.title.contains(searchPhrase, ignoreCase = true)
                }
            }
            if (filterCategory.isNotEmpty()) {
                searchPhrase = ""
                displayItems = menuItems.filter {
                    it.category.contains(filterCategory, ignoreCase = true)
                }
            }
            if (searchPhrase.isEmpty() && filterCategory.isEmpty()) {
                displayItems = menuItems
            }
            MenuItems(displayItems)

        }
    }
}

@Composable
fun MenuItems(menuItems: List<MenuItem>) {
    if (menuItems.isEmpty()) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text( text = "The menu is empty." )
        }
    } else {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            for(menuItem in menuItems) {
                MenuItem(
                    menuItem.title,
                    menuItem.description,
                    menuItem.price,
                    menuItem.image
                )
            }
        }
    }
}

@Composable
fun MenuItem(title: String, description: String, price: String, image: String) {
    val priceFloat = price.toFloat()
    val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val priceString = currencyFormat.format(priceFloat)
    Text(
        text = title,
        fontFamily = karlaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
        )
    Row {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ){
            Text(
                text = description,
                fontFamily = karlaFontFamily,
                fontSize = 14.sp,
                maxLines = 2,
                overflow =TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(0.dp, 14.dp, 0.dp, 0.dp)
            )
            Text(
                text = priceString,
                fontFamily = karlaFontFamily,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(0.dp, 14.dp, 0.dp, 0.dp)
            )
        }
        GlideImage(
            model = image,
            contentDescription = title,
            loading = placeholder(R.drawable.hero),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(0.dp, 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(null)
}