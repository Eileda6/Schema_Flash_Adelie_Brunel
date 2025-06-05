package fr.ensim.android.schemaflash

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import fr.ensim.android.schemaflash.ui.theme.SchemaFlashTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        setContent {
            SchemaFlashTheme {
                val navController = rememberNavController()
                var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
                val coroutineScope = rememberCoroutineScope()

                // Liste des flashcards (de type FlashCardData)
                val firestore = FirebaseFirestore.getInstance()

                LaunchedEffect(Unit) {
                    try {
                        val snapshot = firestore.collection("flashcards").get().await()
                        val cards = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(FlashCardData::class.java)
                        }
                        flashCardList.clear()
                        flashCardList.addAll(cards)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


                NavHost(navController = navController, startDestination = "accueil") {
                    composable("accueil") {
                        PageAccueilScreen(
                            flashCards = flashCardList,
                            onFabClick = { navController.navigate("ajouter") },
                            onCardClick = { flashCard ->
                                val encodedUri = Uri.encode(flashCard.imageUrl)
                                navController.navigate("fiche/${flashCard.titre}/$encodedUri")
                            }
                        )
                        // Ne PAS mettre LaunchedEffect ici ! (déjà fait au-dessus)
                    }

                    composable("ajouter") {
                        AjouterFicheScreen(
                            onImageSelected = { uri ->
                                coroutineScope.launch {
                                    val url = uploadImageToFirebase(uri)
                                    if (url != null) {
                                        selectedImageUri = Uri.parse(url)
                                        navController.navigate("creer")
                                    }
                                }
                            },
                            onRetourAccueil = { navController.navigate("accueil") }
                        )
                    }

                    composable("creer") {
                        if (selectedImageUri != null) {
                            CreerFicheScreen(
                                imageUri = selectedImageUri!!,
                                onRetourAccueil = {
                                    selectedImageUri = null
                                    navController.navigate("accueil") {
                                        popUpTo("accueil") { inclusive = true }
                                    }
                                }
                            )
                        } else {
                            LaunchedEffect(Unit) { navController.navigate("ajouter") }
                        }
                    }

                    composable(
                        route = "fiche/{title}/{imageUri}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("imageUri") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        val imageUriStr = backStackEntry.arguments?.getString("imageUri") ?: ""

                        val fiche = flashCardList.find { it.titre == title }

                        ApprendreFicheScreen(
                            title = title,
                            imageUri = imageUriStr,
                            zones = fiche?.zonestexte ?: emptyList(),
                            onAccueilClick = { navController.navigate("accueil") }
                        )

                    }
                }
            }
        }
    }

    private suspend fun uploadImageToFirebase(uri: Uri): String? {
        return try {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")
            imageRef.putFile(uri).await()
            val downloadUrl = imageRef.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
