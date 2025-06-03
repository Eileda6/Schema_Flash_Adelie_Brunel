@file:OptIn(ExperimentalMaterial3Api::class)
package fr.ensim.android.schemaflash

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import fr.ensim.android.schemaflash.ui.theme.LightBlue
import fr.ensim.android.schemaflash.ui.theme.White

@Composable
fun CreerFicheScreen(imageUri: Uri?, onRetourAccueil: () -> Unit) {
    if (imageUri == null) {
        // Afficher une UI d'erreur ou un écran vide
        Text("Aucune image sélectionnée")
        return
    }
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Schema Flash",
                    color = Color.White,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(end = 32.dp)) },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu, // ou ton propre ImageVector importé
                                contentDescription = "Menu",
                                tint = White // ta couleur personnalisée ici
                            )
                        }

                        MenuDeroulant(
                            expanded = menuExpanded,
                            onDismiss = { menuExpanded = false },
                            onAccueilClick = {
                                menuExpanded = false
                                onRetourAccueil()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightBlue)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, top = 8.dp), // un peu d’espace au-dessus et en-dessous
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { onRetourAccueil() },
                    colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                    modifier = Modifier
                        .widthIn(min = 150.dp, max = 250.dp)  // largeur contrôlée, ni trop large ni trop petite
                        .height(48.dp)  // hauteur un peu plus grande que le standard
                ) {
                    Text("Valider")
                }
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        )
      {
            // 🔘 6 Boutons d’édition
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* undo */ }) {
                    Icon(Icons.Default.Undo, contentDescription = "Annuler")
                }
                IconButton(onClick = { /* redo */ }) {
                    Icon(Icons.Default.Redo, contentDescription = "Rétablir")
                }
                IconButton(onClick = { /* texte */ }) {
                    Icon(Icons.Default.TextFields, contentDescription = "Zone de texte")
                }
                IconButton(onClick = { /* flèche */ }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Flèche")
                }
                IconButton(onClick = { /* crop */ }) {
                    Icon(Icons.Default.Crop, contentDescription = "Rogner")
                }
                IconButton(onClick = { /* télécharger */ }) {
                    Icon(Icons.Default.Download, contentDescription = "Télécharger")
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // 🖼️ Affichage de l’image
            AsyncImage(
                model = imageUri,
                contentDescription = "Image sélectionnée",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.Gray)
            )
        }
    }
}


