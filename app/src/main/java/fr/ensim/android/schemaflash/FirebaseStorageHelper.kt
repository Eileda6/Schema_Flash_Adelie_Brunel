package fr.ensim.android.schemaflash

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

suspend fun uploadImageToFirebase(uri: Uri, context: Context): String? {
    return try {
        val storageRef = FirebaseStorage.getInstance().reference
        val fileName = "images/${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child(fileName)

        imageRef.putFile(uri).await()
        val downloadUrl = imageRef.downloadUrl.await()

        downloadUrl.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
