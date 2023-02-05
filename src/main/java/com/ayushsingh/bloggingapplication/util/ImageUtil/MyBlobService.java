package com.ayushsingh.bloggingapplication.util.ImageUtil;

import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.DeleteSnapshotsOptionType;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MyBlobService {
    @Autowired
    private AzureBlobProperties azureBlobProperties;

    private BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(azureBlobProperties.getConnectionstring()).buildClient();
        BlobContainerClient container = serviceClient.getBlobContainerClient(azureBlobProperties.getContainer());
        return container;
    }

    //list all blob files
    public List<String> listFiles() {
        log.info("List blobs BEGIN");
        BlobContainerClient container = containerClient();
        val list = new ArrayList<String>();
        for (BlobItem blobItem : container.listBlobs()) {
            log.info("Blob {}", blobItem.getName());
            list.add(blobItem.getName());
        }
        log.info("List blobs END");
        return list;
    }

    //download a blob file
    public ByteArrayOutputStream downloadFile(String blobitem) {
        log.info("Download BEGIN {}", blobitem);
        BlobContainerClient containerClient = containerClient();
        BlobClient blobClient = containerClient.getBlobClient(blobitem);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // blobClient.download(os); //download is deprecated
        blobClient.downloadStream(os);
        log.info("Download END");
        return os;
    }

    //upload a blob file
    public String storeFile(String filename, InputStream content, long length) {
        log.info("Azure store file BEGIN {}", filename);
        BlobClient client = containerClient().getBlobClient(filename);
        if (client.exists()) {
            log.warn("The file was already located on azure");
        } else {
            client.upload(content, length);
        }

        log.info("Azure store file END");
        return "File upload successful! "+client.getBlobName();
    }

    //Delete a blob file
    public boolean deleteFile(String filename) {
        log.info("Deleting ", filename);
        BlobContainerClient containerClient = containerClient();
        BlobClient blobClient = containerClient.getBlobClient(filename);
        Response<Boolean> response = blobClient.deleteIfExistsWithResponse(DeleteSnapshotsOptionType.INCLUDE, null,
                null,
                new Context("key", "value"));
        if (response.getStatusCode() == 404) {
            System.out.println("Blob does not exist");
            return false;
        } else {
            System.out.println("Delete blob completed with status %d%n "+response.getStatusCode());
            return true;
        }
    }

}
