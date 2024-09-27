package com.example.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun Profile(navController: NavHostController?) {
    Scaffold(
        floatingActionButton = {
            Button(
                onClick =
                {
                    navController?.navigate(Onboarding.route)
                },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_yellow)),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.profile_logout),
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
            LogoRow()
            HeaderRow(label = stringResource(id = R.string.profile_information))
            InfoRow(
                label = stringResource(id = R.string.label_firstname),
                content = stringResource(id = R.string.example_firstname)
            )
            InfoRow(
                label = stringResource(id = R.string.label_lastname),
                content = stringResource(id = R.string.example_lastname)
            )
            InfoRow(
                label = stringResource(id = R.string.label_email),
                content = stringResource(id = R.string.example_email)
            )
        }
    }
}

@Composable
fun InfoRow(label: String, content: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
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
            fontWeight = FontWeight.Light
        )
        Text(
            text = content,
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
fun ProfilePreview() {
    Profile(null)
}