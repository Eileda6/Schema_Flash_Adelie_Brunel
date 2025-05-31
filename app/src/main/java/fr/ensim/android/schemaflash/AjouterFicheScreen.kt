@file:OptIn(ExperimentalMaterial3Api::class)

package fr.ensim.android.schemaflash

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AjouterFicheScreen(onRetourAccueil: () -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Schema Flash", fontSize = 26.sp)
                },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.mobile_menu_icon),
                                contentDescription = "Menu"
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            MenuDeroulant(
                                expanded = menuExpanded,
                                onDismiss = { menuExpanded = false },
                                onAccueilClick = {
                                    menuExpanded = false
                                    onRetourAccueil()
                                }
                            )

                        }
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Contenu temporairement vide
        }
    }
}

