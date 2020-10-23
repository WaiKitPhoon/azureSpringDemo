package com.springazure.demo.adapter

import com.microsoft.azure.storage.OperationContext
import com.microsoft.azure.storage.StorageException
import com.microsoft.azure.storage.blob.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.util.*

@Component
class BlobContainerAdapter {

    private val logger: Logger = LoggerFactory.getLogger(BlobContainerAdapter::class.java)

    @Autowired
    private val cloudBlobClient: CloudBlobClient? = null

    @Autowired
    private val cloudBlobContainer: CloudBlobContainer? = null

    fun createContainer(containerName: String?): Boolean {
        var containerCreated = false
        var container: CloudBlobContainer? = null
        try {
            container = cloudBlobClient!!.getContainerReference(containerName)
        } catch (e: URISyntaxException) {
            logger.error(e.message)
            e.printStackTrace()
        } catch (e: StorageException) {
            logger.error(e.message)
            e.printStackTrace()
        }
        try {
            containerCreated = container!!.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, BlobRequestOptions(), OperationContext())
        } catch (e: StorageException) {
            logger.error(e.message)
            e.printStackTrace()
        }
        return containerCreated
    }

    fun upload(multipartFile: MultipartFile): URI? {
        var uri: URI? = null
        var blob: CloudBlockBlob? = null
        try {
            blob = cloudBlobContainer!!.getBlockBlobReference(multipartFile.originalFilename)
            blob.upload(multipartFile.inputStream, -1)
            uri = blob.uri
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        } catch (e: StorageException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return uri
    }

    fun listBlobs(containerName: String?): List<URI>? {
        val uris: MutableList<URI> = ArrayList<URI>()
        try {
            val container = cloudBlobClient!!.getContainerReference(containerName)
            for (blobItem in container.listBlobs()) {
                uris.add(blobItem.uri)
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        } catch (e: StorageException) {
            e.printStackTrace()
        }
        return uris
    }

    fun deleteBlob(containerName: String?, blobName: String?) {
        try {
            val container = cloudBlobClient!!.getContainerReference(containerName)
            val blobToBeDeleted = container.getBlockBlobReference(blobName)
            blobToBeDeleted.deleteIfExists()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        } catch (e: StorageException) {
            e.printStackTrace()
        }
    }
}