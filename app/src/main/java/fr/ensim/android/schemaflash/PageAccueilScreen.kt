package fr.ensim.android.schemaflash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --------- Données flashcards ----------
data class FlashCardData(val imageRes: Int, val title: String)

val flashCards = listOf(
    FlashCardData(R.drawable.neurone, "Fiche Neurone"),
    FlashCardData(R.drawable.cell_schema, "Fiche cellule"),
    FlashCardData(R.drawable.corps_humain, "Fiche corps humain"),
    FlashCardData(R.drawable.carte_france, "Fiche carte france"),
    FlashCardData(R.drawable.coeur, "Fiche coeur"),
    FlashCardData(R.drawable.os, "Fiche os")
)

// --------- Page Accueil ----------
@Composable
fun PageAccueilScreen(onFabClick: () -> Unit = {}) {
    Scaffold(
        topBar = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                ) {
                    // Faux bouton menu (3 traits)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(width = 30.dp, height = 4.dp)
                                    .background(Color.Black)
                            )
                        }
                    }

                    // Titre avec effet contour simulé
                    Box {
                        Text(
                            text = "Schema Flash",
                            fontSize = 30.sp,
                            color = Color.Black,
                            modifier = Modifier.offset(x = 1.dp, y = 1.dp)
                        )
                        Text(
                            text = "Schema Flash",
                            fontSize = 30.sp,
                            color = Color.Blue
                        )
                    }
                }

                // Ligne noire de séparation
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_add),
                    contentDescription = "Ajouter"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Bienvenue sur la page d’accueil. Ici tu trouveras toutes tes fiches",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 72.dp)
            ) {
                items(flashCards) { card ->
                    FlashCard(imageRes = card.imageRes, text = card.title)
                }
            }
        }
    }
}

// --------- Fiche individuelle ----------
@Composable
fun FlashCard(imageRes: Int, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }
    }
}
