package com.example.mad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mad.Viewmodel.PortalModel
import com.example.mad.ui.screens.AddPortalScreen

import com.example.mad.ui.screens.PortalOverviewScreen
import com.example.mad.ui.screens.PortalScreens
import com.example.mad.ui.theme.MADTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MADTheme {
                val viewModel: PortalModel = viewModel()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = PortalScreens.PortalOverview.name,
                ) {
                    composable(PortalScreens.PortalOverview.name) {
                        viewModel.portalList.value?.let { it1 -> PortalOverviewScreen(navController = navController, portalList = it1) }
                    }
                    composable(PortalScreens.AddPortal.name) {
                        AddPortalScreen(
                            onAddPortal = { portal ->
                                viewModel.addPortal(portal)
                                navController.navigate(PortalScreens.PortalOverview.name) {
                                    popUpTo(PortalScreens.PortalOverview.name) {
                                        inclusive = true
                                    }
                                }
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MADTheme {

    }
}