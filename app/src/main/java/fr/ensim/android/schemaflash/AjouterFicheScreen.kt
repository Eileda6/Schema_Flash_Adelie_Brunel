@file:OptIn(ExperimentalMaterial3Api::class)

package fr.ensim.android.schemaflash

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.activity.result.contract.ActivityResultContracts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.launch
import androidx.compose.ui.platform.LocalContext

@Composable
fun AjouterFicheScreen(onRetourAccueil: () -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Launcher pour ouvrir le sélecteur de fichiers
    val launcherFile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Ici tu peux gérer le fichier choisi
            println("Fichier sélectionné : $it")
            // Par exemple, afficher un Toast ou charger le fichier
        }
    }

    // Launcher pour ouvrir l'appareil photo et récupérer une photo en Bitmap
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            // Gère ici le bitmap retourné par la caméra
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Schema Flash", fontSize = 26.sp) },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.mobile_menu_icon),
                                contentDescription = "Menu"
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
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .border(1.dp, Color.Black)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Créer une flashcard à partir de", fontSize = 20.sp)

            // Bouton 1 : Fichier
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    launcherFile.launch("*/*") // ouvre tous les types de fichiers
                }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_upload),
                        contentDescription = "Importer fichier",
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Fichier", fontSize = 18.sp)
            }

            Text(text = "OU", fontSize = 16.sp)

            // Bouton 2 : Appareil photo
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    launcherCamera.launch() // lance l'appareil photo
                }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_camera),
                        contentDescription = "Prendre une photo",
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Appareil photo", fontSize = 18.sp)
            }
        }
    }
}
