package fr.ensim.android.schemaflash

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import androidx.compose.runtime.mutableStateListOf

data class CompletionZone(
    val label: String = "",
    val correctAnswer: String = ""
)

data class FlashCardData(
    val titre: String = "",
    val imageUrl: String = "",
    val zonestexte: List<CompletionZone> = emptyList()
)


private const val PACKAGE_NAME = "fr.ensim.android.schemaflash"

val flashCardList = mutableStateListOf<FlashCardData>()

fun ajouterNouvelleFiche(fiche: FlashCardData) {
    flashCardList.add(fiche)
}

fun uploadImageAndAddFiche(
    uri: Uri,
    titre: String,
    zones: List<CompletionZone>,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val storageRef = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}.jpg")

    storageRef.putFile(uri)
        .addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                val newFiche = FlashCardData(
                    titre = titre.ifBlank { "Sans titre" },
                    imageUrl = downloadUrl.toString(),
                    zonestexte = zones
                )
                FirebaseFirestore.getInstance()
                    .collection("flashcards")
                    .add(newFiche)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError(e.message ?: "Erreur ajout Firestore") }
            }.addOnFailureListener { e -> onError(e.message ?: "Erreur get URL") }
        }
        .addOnFailureListener { e -> onError(e.message ?: "Erreur upload") }
}

object FlashcardRepository {
    private val db = FirebaseFirestore.getInstance()

    val flashCardList = mutableStateListOf<FlashCardData>().apply {
        addAll(
            listOf(
                FlashCardData(
                    imageUrl = "android.resource://fr.ensim.android.schemaflash/${R.drawable.neurone}",
                    titre = "Neurone",
                    zonestexte = listOf(
                        CompletionZone("1", "dendrite"),
                        CompletionZone("2", "axone"),
                        CompletionZone("3", "noyau"),
                        CompletionZone("4", "gaine de myéline"),
                        CompletionZone("5", "terminaison axonale")
                    )
                ),
                FlashCardData(
                    imageUrl = "android.resource://$PACKAGE_NAME/${R.drawable.cell_schema}",
                    titre = "Fiche cellule",
                    zonestexte = listOf(CompletionZone("1", "noyau"))
                )
                // Ajoute d'autres fiches si besoin
            )
        )
    }

    fun ajouterNouvelleFiche(fiche: FlashCardData) {
        flashCardList.add(fiche)
    }

    suspend fun chargerFichesDepuisFirebase() {
        val result = db.collection("flashcards").get().await()
        flashCardList.clear()
        for (doc in result) {
            val fiche = doc.toObject(FlashCardData::class.java)
            flashCardList.add(fiche)
        }
    }
}
