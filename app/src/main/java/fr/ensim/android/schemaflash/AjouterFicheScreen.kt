@file:OptIn(ExperimentalMaterial3Api::class)

package fr.ensim.android.schemaflash

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

import android.Manifest
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

@Composable
fun AjouterFicheScreen(
    onImageSelected: (Uri) -> Unit,
    onRetourAccueil: () -> Unit
) {
    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PermissionChecker.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }




    // Création du fichier temporaire pour la photo
    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir = context.cacheDir
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher pour ouvrir le fichier (image)
    val launcherFile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onImageSelected(it)
        }
    }

    // Launcher pour prendre une photo et enregistrer dans photoUri
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            val uri = photoUri // copie locale
            if (uri != null) {
                onImageSelected(uri)
            }
        }
    }
    // Quand tu veux lancer la caméra, vérifie la permission :
    fun tryLaunchCamera() {
        val photoFile = createImageFile(context)
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
        photoUri = uri
        launcherCamera.launch(uri)
    }




    var menuExpanded by remember { mutableStateOf(false) }

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
                    launcherFile.launch("image/*") // ouvre seulement les images
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
                    if (hasCameraPermission) {
                        tryLaunchCamera()
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
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
