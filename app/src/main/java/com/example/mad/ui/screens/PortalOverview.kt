package com.example.mad.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mad.R
import com.example.mad.Viewmodel.PortalModel
import com.example.mad.model.Portal

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PortalOverviewScreen(navController: NavHostController, portalList: List<Portal>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        content = {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                PortalList(navController, portalList)
            }
        },
        floatingActionButton = {
            PortalOverviewScreenFab(navController)
        }
    )
}

@Composable
private fun PortalList(navController: NavHostController, portalList: List<Portal>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(portalList) { portal ->
            PortalItem(portal) {
                navController.navigate("addPortal")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PortalItem(portal: Portal, onItemClick: () -> Unit) {
    openPortalUrl(LocalContext.current, portal.url)
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp,
        onClick = onItemClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = portal.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = portal.url,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun PortalOverviewScreenFab(navController: NavHostController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(PortalScreens.AddPortal.route)
        },
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight(),
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}

fun openPortalUrl(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}
