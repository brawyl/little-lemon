package com.example.littlelemon

import android.net.http.HeaderBlock
import android.os.Bundle
import android.preference.PreferenceActivity.Header
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.ui.theme.LittleLemonTheme

val karlaFontFamily = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)

@Composable
fun Onboarding(image: Painter, description: String, modifier: Modifier = Modifier) {
    Column(
        Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(0.9f, true)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = image,
                    contentDescription = description,
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier
                        .fillMaxWidth(0.5f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
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
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(80.dp)
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
            FormLine(label = stringResource(id = R.string.onboarding_firstname))
            FormLine(label = stringResource(id = R.string.onboarding_lastname))
            FormLine(label = stringResource(id = R.string.onboarding_email))
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .weight(0.1f, true)
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_yellow)),
                shape = RoundedCornerShape(5.dp),
                modifier = modifier
                    .weight(0.1f, false)
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

@Composable
fun FormLine(label: String, modifier: Modifier = Modifier) {
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 10.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Onboarding(painterResource(id = R.drawable.logo), "Little Lemon Logo")
    }
}