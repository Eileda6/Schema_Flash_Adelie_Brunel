@file:OptIn(ExperimentalMaterial3Api::class)

package fr.ensim.android.schemaflash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.ensim.android.schemaflash.ui.theme.LightBlue
import fr.ensim.android.schemaflash.ui.theme.White
import fr.ensim.android.schemaflash.ui.theme.Blue1
import androidx.compose.material3.Divider

// ------------ Données ------------
data class FlashCardData(val imageRes: Int, val title: String)

val flashCards = listOf(
    FlashCardData(R.drawable.neurone, "Fiche Neurone"),
    FlashCardData(R.drawable.cell_schema, "Fiche cellule"),
    FlashCardData(R.drawable.corps_humain, "Fiche corps humain"),
    FlashCardData(R.drawable.carte_france, "Fiche carte france"),
    FlashCardData(R.drawable.coeur, "Fiche coeur"),
    FlashCardData(R.drawable.os, "Fiche os")
)

// ------------ Barre du haut réutilisable ------------
@Composable
fun PageAccueilScreen(onFabClick: () -> Unit,
                      onCardClick: (FlashCardData) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(32.dp))
                        Text(
                            text = "Schema Flash",
                            color = Color.White,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(end = 32.dp)
                        )
                    }
                },

                navigationIcon = {
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu, // ou ton propre ImageVector importé
                                contentDescription = "Menu",
                                tint = White // ta couleur personnalisée ici
                            )

                        }
                        MenuDeroulant(
                            expanded = expanded,
                            onDismiss = { expanded = false },
                            onAccueilClick = { /* rien à faire, on est déjà sur accueil */ }
                        )

                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .border(1.dp, Color.Black)

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = LightBlue // ✅ bonne façon de définir la couleur de fond
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_add),
                    contentDescription = "Ajouter",
                    tint = Color.White // ou autre couleur du pictogramme
                )
            }

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            Text(
                text = "Bienvenue sur la page d’accueil. Ici tu trouveras toutes tes fiches",
                fontSize = 24.sp,
                color = Blue1,
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
                    FlashCard(
                        imageRes = card.imageRes,
                        text = card.title,
                        onClick = { onCardClick(card) }
                    )
                }
            }
        }
    }
}



// ------------ Carte individuelle ------------
@Composable
fun FlashCard(imageRes: Int, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick) // <-- AJOUTER CE CLICKABLE
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
                ambientColor = Blue1,
                spotColor = Blue1
            ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
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


@Composable
fun ImageTestScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.neurone), // remplace par ton image
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}
