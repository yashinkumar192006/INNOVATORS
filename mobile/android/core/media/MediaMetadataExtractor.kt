package com.trinetra.mobile.android.core.media

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Environment
import java.io.File

/**
 * Media metadata extraction for investigation context.
 * Person A implementation - client-side extraction.
 * Person B owns backend persistence and API contracts.
 */
object MediaMetadataExtractor {

    /** Extract metadata from a media file */
    data class Metadata(
        /** File path */
        val filePath: String,
        /** File name */
        val fileName: String,
        /** File size in bytes */
        val fileSize: Long,
        /** MIME type */
        val mimeType: String?,
        /** Duration in milliseconds (for video/audio) */
        val durationMs: Long?,
        /** Width in pixels (for image/video) */
        val width: Int?,
        /** Height in pixels (for image/video) */
        val height: Int?,
        /** Creation date */
        val creationDate: Long?,
        /** Modification date */
        val modificationDate: Long?,
        /** SHA-256 fingerprint */
        val sha256Fingerprint: String?,
        /** Perceptual hash */
        val perceptualHash: String?
    )

    /** Extract metadata from a media file */
    fun extractMetadata(file: File): Metadata? {
        try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(file.absolutePath)

            val name = file.name
            val size = file.length()
            val mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
            val duration = try {
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
            } catch (e: Exception) {
                null
            }
            val width = try {
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_WIDTH).toInt()
            } catch (e: Exception) {
                null
            }
            val height = try {
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HEIGHT).toInt()
            } catch (e: Exception) {
                null
            }
            val creation = try {
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CREATION_TIME)
            } catch (e: Exception) {
                null
            }
            val modification = try {
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MODIFICATION_TIME)
            } catch (e: Exception) {
                null
            }
            val sha256 = FileFingerprint.computeFileHash(file)
            val perceptual = PerceptualHash.computeHash(BitmapFactory.decodeFile(file.absolutePath))

            return Metadata(
                filePath = file.absolutePath,
                fileName = name,
                fileSize = size,
                mimeType = mimeType,
                durationMs = duration,
                width = width,
                height = height,
                creationDate = creation?.toLong(),
                modificationDate = modification?.toLong(),
                sha256Fingerprint = sha256,
                perceptualHash = perceptual
            )
        } catch (e: Exception) {
            // Fallback: minimal metadata
            return try {
                Metadata(
                    filePath = file.absolutePath,
                    fileName = file.name,
                    fileSize = file.length(),
                    mimeType = null,
                    durationMs = null,
                    width = null,
                    height = null,
                    creationDate = null,
                    modificationDate = null,
                    sha256Fingerprint = FileFingerprint.computeFileHash(file),
                    perceptualHash = PerceptualHash.computeHash(null)
                )
            } catch (e2: Exception) {
                null
            }
        }
    }
}