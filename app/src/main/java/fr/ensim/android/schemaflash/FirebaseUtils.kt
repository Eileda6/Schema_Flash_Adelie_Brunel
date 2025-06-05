package fr.ensim.android.schemaflash

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

/**
 * Upload une image vers Firebase Storage et retourne l'URL de téléchargement.
 *
 * @param imageUri Uri de l'image à uploader
 * @param context Contexte Android (nécessaire si besoin, mais pas utilisé ici)
 * @return URL de l'image uploadée, ou null en cas d'erreur
 */

