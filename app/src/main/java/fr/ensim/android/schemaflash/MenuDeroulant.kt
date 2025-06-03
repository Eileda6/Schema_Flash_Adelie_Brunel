package fr.ensim.android.schemaflash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.ensim.android.schemaflash.ui.theme.LightBlue

@Composable
fun MenuDeroulant(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onAccueilClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = Modifier.background(LightBlue)
    ) {
        DropdownMenuItem(
            text = { Text("Page d'accueil", color = Color.White) },
            onClick = {
                onDismiss()
                onAccueilClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue)
        )
        DropdownMenuItem(
            text = { Text("Une idée d'amélioration ?", color = Color.White) },
            onClick = { onDismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue, shape = RoundedCornerShape(4.dp))
        )
        DropdownMenuItem(
            text = { Text("Besoin d'aide", color = Color.White) },
            onClick = { onDismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue, shape = RoundedCornerShape(4.dp))
        )
        DropdownMenuItem(
            text = { Text("À propos de nous", color = Color.White) },
            onClick = { onDismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue, shape = RoundedCornerShape(4.dp))
        )
    }
}
