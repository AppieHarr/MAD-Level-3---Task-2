package com.example.mad.ui.screens

import android.util.Patterns
import android.webkit.URLUtil
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mad.model.Portal

@Composable
fun AddPortalScreen(
    onAddPortal: (Portal) -> Unit,
    navController: NavHostController,
    onNavigateBack: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var url by rememberSaveable { mutableStateOf("https://") }

    fun isValidUrl(url: String): Boolean {
        return URLUtil.isValidUrl(url) && Patterns.WEB_URL.matcher(url).matches()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Portal Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Portal URL") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Uri
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isValidUrl(url)) {
                        onAddPortal(Portal(name = name, url = url))
                        navController.navigateUp()
                    }
                }
            )
        )

        Text(
            text = "URL should start with 'https://' and have at least 12 characters",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(top = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    onNavigateBack()
                    navController.navigateUp()
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "CANCEL")
            }

            Button(
                onClick = {
                    if (isValidUrl(url)) {
                        onAddPortal(Portal(name = name, url = url))
                        navController.navigateUp()
                    }
                },
                enabled = name.isNotBlank() && isValidUrl(url)
            ) {
                Text(text = "ADD")
            }
        }
    }
}
