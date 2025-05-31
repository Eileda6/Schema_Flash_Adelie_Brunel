package fr.ensim.android.schemaflash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.ensim.android.schemaflash.ui.theme.SchemaFlashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchemaFlashTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "accueil") {
                    composable("accueil") {
                        PageAccueilScreen(
                            onFabClick = {
                                navController.navigate("ajouter")
                            }
                        )
                    }
                    composable("ajouter") {
                        AjouterFicheScreen(
                            onRetourAccueil = {
                                navController.popBackStack() // ← revient à la page précédente (accueil)
                            }
                        )
                    }
                }
            }
        }
    }
}
