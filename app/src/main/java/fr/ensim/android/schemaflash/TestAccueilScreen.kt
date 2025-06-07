package fr.ensim.android.schemaflash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestAccueilScreen(
    title: String,
    onAccueilClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Accueil", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2196F3))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Titre reçu :",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 28.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onAccueilClick) {
                Text("Retour")
            }
        }
    }
}
