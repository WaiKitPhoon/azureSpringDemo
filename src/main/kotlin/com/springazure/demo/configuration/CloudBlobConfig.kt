package com.springazure.demo.configuration

import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.StorageException
import com.microsoft.azure.storage.blob.CloudBlobClient
import com.microsoft.azure.storage.blob.CloudBlobContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import java.net.URISyntaxException
import java.security.InvalidKeyException


@Configuration
class BeanConfig {

    @Autowired
    private val environment: Environment? = null

    @Bean
    @Throws(URISyntaxException::class, StorageException::class, InvalidKeyException::class)
    fun cloudBlobClient(): CloudBlobClient {
        val storageAccount: CloudStorageAccount = CloudStorageAccount.parse(environment?.getProperty("azure.storage.ConnectionString"))
        return storageAccount.createCloudBlobClient()
    }

    @Bean
    @Throws(URISyntaxException::class, StorageException::class, InvalidKeyException::class)
    fun testBlobContainer(): CloudBlobContainer {
        return cloudBlobClient().getContainerReference(environment?.getProperty("azure.storage.container.name"))
    }
}