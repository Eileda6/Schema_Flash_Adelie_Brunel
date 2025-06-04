@file:OptIn(ExperimentalMaterial3Api::class)

package fr.ensim.android.schemaflash

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.ensim.android.schemaflash.ui.theme.Blue1
import fr.ensim.android.schemaflash.ui.theme.LightBlue
import fr.ensim.android.schemaflash.ui.theme.White

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
                            modifier = Modifier.padding(end = 32.dp)
                        )
                    }
                },

                navigationIcon = {
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = White
                            )
                        }
                        MenuDeroulant(
                            expanded = expanded,
                            onDismiss = { expanded = false },
                            onAccueilClick = { /* déjà sur accueil */ }
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
                containerColor = LightBlue
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_add),
                    contentDescription = "Ajouter",
                    tint = Color.White
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
                text = "Mes fiches",
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
                items(flashCardList) { card ->
                    FlashCard(
                        imageUri = card.imageUri,
                        text = card.title,
                        onClick = { onCardClick(card) }
                    )
                }
            }
        }
    }
}


@Composable
fun FlashCard(imageUri: String, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
                ambientColor = Blue1,
                spotColor = Blue1
            ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUri,
                contentDescription = text,
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
