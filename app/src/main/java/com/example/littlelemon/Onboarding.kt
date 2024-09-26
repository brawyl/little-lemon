package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import android.telecom.Call.Details
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

val karlaFontFamily = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Onboarding(navController: NavHostController) {
    var isValid by remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .padding(0.dp, 0.dp, 0.dp, 80.dp)
    ) {
        Column {
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
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.primary_green))
                    .height(120.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_prompt),
                    fontSize = 25.sp,
                    lineHeight = 25.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.highlight_white),
                    fontFamily = karlaFontFamily
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_information),
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    color = colorResource(id = R.color.highlight_black),
                    fontFamily = karlaFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
            FormLabel(stringResource(id = R.string.onboarding_firstname))
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
                    placeholder = { Text(text = stringResource(id = R.string.onboarding_firstname_placeholder)) },
                    isError = firstName.isNotEmpty(),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            FormLabel(stringResource(id = R.string.onboarding_lastname))
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
                    placeholder = { Text(text = stringResource(id = R.string.onboarding_lastname_placeholder)) },
                    isError = lastName.isNotEmpty(),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            FormLabel(stringResource(id = R.string.onboarding_email))
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
                    placeholder = { Text(text = stringResource(id = R.string.onboarding_email_placeholder)) },
                    isError = lastName.isNotEmpty(),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick =
                    {
                        if (isValid) {
                            navController.navigate(Home.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_yellow)),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.onboarding_register),
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = colorResource(id = R.color.highlight_black),
                        fontFamily = karlaFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun FormLabel(label: String, modifier: Modifier = Modifier) {
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