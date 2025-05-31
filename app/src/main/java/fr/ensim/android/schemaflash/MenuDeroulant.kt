package fr.ensim.android.schemaflash



import androidx.compose.material3.*
import androidx.compose.runtime.Composable


@Composable
fun MenuDeroulant(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onAccueilClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = { Text("Page d'accueil") },
            onClick = {
                onDismiss()
                onAccueilClick()
            }
        )
        DropdownMenuItem(
            text = { Text("Une idée d'amélioration ?") },
            onClick = { onDismiss() }
        )
        DropdownMenuItem(
            text = { Text("Besoin d'aide") },
            onClick = { onDismiss() }
        )
        DropdownMenuItem(
            text = { Text("À propos de nous") },
            onClick = { onDismiss() }
        )
    }
}
