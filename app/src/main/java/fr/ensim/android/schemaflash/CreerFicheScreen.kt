@file:OptIn(ExperimentalMaterial3Api::class)

package fr.ensim.android.schemaflash

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import fr.ensim.android.schemaflash.ui.theme.LightBlue
import fr.ensim.android.schemaflash.ui.theme.White
import fr.ensim.android.schemaflash.flashCardList

@Composable
fun CreerFicheScreen(
    imageUri: Uri?,
    onRetourAccueil: () -> Unit
) {
    if (imageUri == null) {
        Text("Aucune image sélectionnée")
        return
    }

    var menuExpanded by remember { mutableStateOf(false) }
    var titre by remember { mutableStateOf("") }
    var zones by remember { mutableStateOf(listOf<CompletionZone>()) }
    var newZoneLabel by remember { mutableStateOf("") }
    var newZoneAnswer by remember { mutableStateOf("") }
    var showZoneDialog by remember { mutableStateOf(false) }

    val db = FirebaseFirestore.getInstance()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Schema Flash", color = Color.White, fontSize = 30.sp)
                },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = White)
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
                    .padding(bottom = 24.dp, top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        if (imageUri != null) {
                            uploadImageAndAddFiche(
                                uri = imageUri,
                                titre = titre,
                                zones = zones,
                                onSuccess = {
                                    println("Fiche ajoutée avec succès !")
                                    onRetourAccueil()
                                },
                                onError = { message ->
                                    println("Erreur : $message")
                                    // Tu peux ici afficher un Snackbar ou Toast pour informer l'utilisateur
                                }
                            )
                        }
                    },
                    enabled = imageUri != null,
                    colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
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
        ) {
            OutlinedTextField(
                value = titre,
                onValueChange = { titre = it },
                label = { Text("Titre de la fiche") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { showZoneDialog = true }) {
                    Icon(Icons.Default.TextFields, contentDescription = "Zone de texte")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Flèche")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Download, contentDescription = "Télécharger")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = imageUri,
                contentDescription = "Image sélectionnée",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // fixe la hauteur pour tester
                    .border(1.dp, Color.Gray),
                contentScale = ContentScale.Fit
            )


            Spacer(Modifier.height(12.dp))

            Text("Zones ajoutées :", style = MaterialTheme.typography.labelLarge)
            zones.forEach { zone ->
                Text("• ${zone.label} : ${zone.correctAnswer}")
            }
        }

        if (showZoneDialog) {
            AlertDialog(
                onDismissRequest = { showZoneDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        if (newZoneLabel.isNotBlank() && newZoneAnswer.isNotBlank()) {
                            zones = zones + CompletionZone(newZoneLabel.trim(), newZoneAnswer.trim())
                            newZoneLabel = ""
                            newZoneAnswer = ""
                            showZoneDialog = false
                        }
                    }) {
                        Text("Ajouter")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showZoneDialog = false }) {
                        Text("Annuler")
                    }
                },
                title = { Text("Ajouter une zone") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newZoneLabel,
                            onValueChange = { newZoneLabel = it },
                            label = { Text("Nom de la zone") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newZoneAnswer,
                            onValueChange = { newZoneAnswer = it },
                            label = { Text("Réponse correcte") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        }
    }
}
