package fr.ensim.android.schemaflash

import androidx.compose.runtime.mutableStateListOf


data class CompletionZone(
    val label: String,
    val correctAnswer: String
)

data class FlashCardData(
    val imageUri: String, // Uri sous forme de chaîne
    val title: String,
    val zones: List<CompletionZone>
)

// Remplace avec ton propre nom de package !
private const val PACKAGE_NAME = "fr.ensim.android.schemaflash"

val flashCardList = mutableStateListOf<FlashCardData>().apply {
    addAll(
        listOf(
            FlashCardData(
                imageUri = "android.resource://$PACKAGE_NAME/${R.drawable.neurone}",
                title = "Neurone",
                zones = listOf(
                    CompletionZone("1", "dendrite"),
                    CompletionZone("2", "axone"),
                    CompletionZone("3", "noyau"),
                    CompletionZone("4", "gaine de myéline"),
                    CompletionZone("5", "terminaison axonale")
                )
            ),
            FlashCardData(
                imageUri = "android.resource://$PACKAGE_NAME/${R.drawable.cell_schema}",
                title = "Fiche cellule",
                zones = listOf(CompletionZone("1", "noyau"))
            ),
            FlashCardData(
                imageUri = "android.resource://$PACKAGE_NAME/${R.drawable.corps_humain}",
                title = "Fiche corps humain",
                zones = listOf(CompletionZone("1", "noyau"))
            ),
            FlashCardData(
                imageUri = "android.resource://$PACKAGE_NAME/${R.drawable.carte_france}",
                title = "Fiche carte france",
                zones = listOf(CompletionZone("1", "noyau"))
            ),
            FlashCardData(
                imageUri = "android.resource://$PACKAGE_NAME/${R.drawable.coeur}",
                title = "Fiche coeur",
                zones = listOf(CompletionZone("1", "noyau"))
            ),
            FlashCardData(
                imageUri = "android.resource://$PACKAGE_NAME/${R.drawable.os}",
                title = "Fiche os",
                zones = listOf(CompletionZone("1", "noyau"))
            )
        )
    )
}

// Fonction pour ajouter une nouvelle fiche dynamique
fun ajouterNouvelleFiche(fiche: FlashCardData) {
    flashCardList.add(fiche)
}



