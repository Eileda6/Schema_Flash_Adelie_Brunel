package fr.ensim.android.schemaflash

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.ensim.android.schemaflash.ui.theme.SchemaFlashTheme
import androidx.navigation.NavType
import androidx.navigation.navArgument

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchemaFlashTheme {
                val navController = rememberNavController()
                var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

                NavHost(navController = navController, startDestination = "accueil") {
                    composable("accueil") {
                        PageAccueilScreen(
                            onFabClick = { navController.navigate("ajouter") },
                            onCardClick = { flashCard ->
                                navController.navigate("fiche/${flashCard.title}/${flashCard.imageRes}")
                            }
                        )

                    }
                    composable("ajouter") {
                        AjouterFicheScreen(
                            onImageSelected = { uri ->
                                selectedImageUri = uri
                                navController.navigate("creer")
                            },
                            onRetourAccueil = { navController.navigate("accueil") }
                        )
                    }
                    composable("creer") {
                        CreerFicheScreen(
                            imageUri = selectedImageUri,
                            onRetourAccueil = { navController.navigate("accueil") }
                        )
                    }
                    composable(
                        route = "fiche/{title}/{imageRes}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("imageRes") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        val imageRes = backStackEntry.arguments?.getInt("imageRes") ?: 0

                        ApprendreFicheScreen(
                            title = title,
                            imageRes = imageRes,
                            onAccueilClick = { navController.navigate("accueil") }
                        )
                    }




                }

            }
        }
    }
}
