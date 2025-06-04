@file:OptIn(ExperimentalMaterial3Api::class)

package fr.ensim.android.schemaflash

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import fr.ensim.android.schemaflash.ui.theme.LightBlue
import fr.ensim.android.schemaflash.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun ApprendreFicheScreen(
    title: String,
    imageUri: String,
    zones: List<CompletionZone>,
    onAccueilClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val userInputs = remember { mutableStateMapOf<String, String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, color = Color.White) },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                        MenuDeroulant(
                            expanded = expanded,
                            onDismiss = { expanded = false },
                            onAccueilClick = onAccueilClick
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightBlue)
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Affichage de l'image à partir de l'URI
            AsyncImage(
                model = imageUri,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Champs pour les zones à compléter
            zones.forEach { zone ->
                val value = userInputs[zone.label] ?: ""
                val isCorrect = value.trim().equals(zone.correctAnswer, ignoreCase = true)

                OutlinedTextField(
                    value = value,
                    onValueChange = { userInputs[zone.label] = it },
                    label = { Text("Zone ${zone.label}") },
                    isError = value.isNotBlank() && !isCorrect,
                    supportingText = {
                        if (value.isNotBlank()) {
                            if (isCorrect) Text("✅ Correct") else Text("❌ Incorrect")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val allCorrect = zones.all { zone ->
                        userInputs[zone.label]?.trim()?.equals(zone.correctAnswer, ignoreCase = true) == true
                    }
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            if (allCorrect) "🎉 Toutes les réponses sont correctes !" else "❌ Certaines réponses sont incorrectes."
                        )
                        if (allCorrect) {
                            kotlinx.coroutines.delay(1000)
                            onAccueilClick()
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Valider")
            }
        }
    }
}
