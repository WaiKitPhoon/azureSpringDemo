package com.springazure.demo.controller

import com.springazure.demo.adapter.BlobContainerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI


@RestController
@RequestMapping("blob")
class BlobController {

    @Autowired
    private val azureBlobAdapter: BlobContainerAdapter? = null

    @PostMapping("/container")
    fun createContainer(@RequestBody containerName: String?): ResponseEntity<*>? {
        val created: Boolean = azureBlobAdapter!!.createContainer(containerName)
        return ResponseEntity.ok(created)
    }

    @PostMapping
    fun upload(@RequestParam multipartFile: MultipartFile?): ResponseEntity<*>? {
        val url: URI? = azureBlobAdapter?.upload(multipartFile!!)
        return ResponseEntity.ok<Any>(url!!)
    }

    @GetMapping("/blobs")
    fun getAllBlobs(@RequestParam containerName: String?): ResponseEntity<*>? {
        val uris: List<URI>? = azureBlobAdapter?.listBlobs(containerName)
        return ResponseEntity.ok<List<URI>>(uris!!)
    }

    @DeleteMapping
    fun delete(@RequestParam containerName: String?, @RequestParam blobName: String?): ResponseEntity<*>? {
        azureBlobAdapter?.deleteBlob(containerName, blobName)
        return ResponseEntity.ok().build<Any>()
    }
}