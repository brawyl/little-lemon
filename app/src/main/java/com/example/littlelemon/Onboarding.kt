package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

val karlaFontFamily = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Onboarding(navController: NavHostController?) {
    val prefs = LocalContext.current.getSharedPreferences(stringResource(id = R.string.prefs_title), Context.MODE_PRIVATE)

    var isValid by remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        floatingActionButton = {
            Button(
                onClick =
                {
                    if (isValid) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Registration successful!")
                        }

                        prefs.edit().putString("firstname", firstName).apply()
                        prefs.edit().putString("lastname", lastName).apply()
                        prefs.edit().putString("email", email).apply()
                        prefs.edit().putString("destination", Home.route).apply()

                        navController?.navigate(Home.route)
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Registration unsuccessful. Please enter all data.")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_yellow)),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_register),
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    color = colorResource(id = R.color.highlight_black),
                    fontFamily = karlaFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { _ ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .padding(0.dp, 0.dp, 0.dp, 80.dp)
        ) {
            Column {
                LogoRow()
                OnboardingHeroRow(label = stringResource(id = R.string.onboarding_prompt))
                HeaderRow(label = stringResource(id = R.string.onboarding_information))
                LabelRow(stringResource(id = R.string.label_firstname))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp)
                ) {
                    OutlinedTextField(
                        value = firstName,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { input ->
                            firstName = input
                            isValid = input.isNotEmpty()
                        },
                        placeholder = { Text(text = stringResource(id = R.string.example_firstname)) },
                        isError = firstName.isNotEmpty(),
                        shape = RoundedCornerShape(5.dp),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                LabelRow(stringResource(id = R.string.label_lastname))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp)
                ) {
                    OutlinedTextField(
                        value = lastName,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { input ->
                            lastName = input
                            isValid = input.isNotEmpty()
                        },
                        placeholder = { Text(text = stringResource(id = R.string.example_lastname)) },
                        isError = lastName.isNotEmpty(),
                        shape = RoundedCornerShape(5.dp),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                LabelRow(stringResource(id = R.string.label_email))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = { input ->
                            email = input
                            isValid = input.isNotEmpty()
                        },
                        placeholder = { Text(text = stringResource(id = R.string.example_email)) },
                        isError = lastName.isNotEmpty(),
                        shape = RoundedCornerShape(5.dp),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun LogoRow() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(0.5f)
        )
    }
}

@Composable
fun OnboardingHeroRow(label: String,) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.primary_green))
            .height(120.dp)
    ) {
        Text(
            text = label,
            fontSize = 25.sp,
            lineHeight = 25.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.highlight_white),
            fontFamily = karlaFontFamily
        )
    }
}

@Composable
fun HeaderRow(label: String,) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(60.dp)
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            lineHeight = 20.sp,
            color = colorResource(id = R.color.highlight_black),
            fontFamily = karlaFontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LabelRow(label: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            color = colorResource(id = R.color.highlight_black),
            textAlign = TextAlign.Left,
            fontFamily = karlaFontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding(null)
}