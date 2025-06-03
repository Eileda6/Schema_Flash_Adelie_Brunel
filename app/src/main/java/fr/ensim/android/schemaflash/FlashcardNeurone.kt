package fr.ensim.android.schemaflash


data class CompletionZone(
    val label: String,
    val correctAnswer: String
)

data class FlashCardData(
    val imageRes: Int,
    val title: String,
    val zones: List<CompletionZone>
)
val flashCards = listOf(
    FlashCardData(
        imageRes = R.drawable.neurone,
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
        imageRes = R.drawable.cell_schema,
        "Fiche cellule",
        zones = listOf(
            CompletionZone("1", "noyau")
        )
    ),
    FlashCardData(
        imageRes = R.drawable.corps_humain,
        "Fiche corps humain",
        zones = listOf(
            CompletionZone("1", "noyau")
        )
    ),
    FlashCardData(
        imageRes = R.drawable.carte_france,
        "Fiche carte france",
        zones = listOf(
            CompletionZone("1", "noyau")
        )
    ),
    FlashCardData(
        imageRes = R.drawable.coeur,
        "Fiche coeur",
        zones = listOf(
            CompletionZone("1", "noyau")
        )
    ),
    FlashCardData(
        imageRes = R.drawable.os,
        "Fiche os",
        zones = listOf(
            CompletionZone("1", "noyau")
        )
    )
)



