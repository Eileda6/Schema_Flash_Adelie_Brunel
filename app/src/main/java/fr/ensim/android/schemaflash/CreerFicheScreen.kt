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

@Composable
fun CreerFicheScreen(imageUri: Uri?, onRetourAccueil: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Créer une fiche") },
                navigationIcon = {
                    IconButton(onClick = onRetourAccueil) {
                        Icon(painterResource(R.drawable.mobile_menu_icon), contentDescription = "Retour")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            imageUri?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.8f).aspectRatio(1f)
                )
            } ?: Text("Aucune image sélectionnée")
        }
    }
}

