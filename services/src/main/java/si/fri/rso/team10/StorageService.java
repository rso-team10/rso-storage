package si.fri.rso.team10;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import si.fri.rso.team10.configuration.ConfigurationProperties;
import si.fri.rso.team10.dto.ListenInstance;
import si.fri.rso.team10.dto.UploadDate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Arrays;

@RequestScoped
public class StorageService {
    private static final String KEY = "ya29.GluPBgrMzFTPHfuKVMAUdL0zNdsBEB6BpbyebBTfLIv9ZCkc6k_4EbH4P5vCNygN08kskzD7ZFIB_iBZTx2yjYpOzkWtsrxmFKv1uxiaYNtIoWo0tAJUgRRZVQIX";
    private static final String BUCKET_NAME = "rso-bucket";
    private static final String FILE_PREFIX = "music/track-";
    private static final String FILE_SUFFIX = ".mp3";

    @Inject
    private ConfigurationProperties configProps;

    public boolean uploadFile(Long id, File file) {
        var storageService = getStorageInstance();
        var fileName = FILE_PREFIX + id + FILE_SUFFIX;

        var blobId = BlobId.of(BUCKET_NAME, fileName);
        var blobInfo = BlobInfo.newBuilder(blobId).setAcl(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))).build();

        try {
            storageService.create(blobInfo, Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public String getFileUrl(Long id) {
        var storageService = getStorageInstance();
        var fileName = FILE_PREFIX + id + FILE_SUFFIX;

        try {
            var blob = storageService.get(BlobId.of(BUCKET_NAME, fileName));

            return blob == null ? null : blob.getMediaLink();
        } catch (StorageException e) {
            // token error probably
            e.printStackTrace();

            return "Permissions be fucked?";
        }
    }

    private Storage getStorageInstance() {
        // when deployed inside k8s, we can use the default instance which should have the necessary permissions
        return StorageOptions.getDefaultInstance().getService();

        //return StorageOptions.newBuilder().setCredentials(GoogleCredentials.create(new AccessToken(configProps.getAccessToken(), null))).build();
    }

    public boolean recordStream(Long id) {
        var listen = new ListenInstance(id);
        try {
            var httpClientListen = HttpClient.newBuilder().build();
            var httpRequestListen = HttpRequest.newBuilder(URI.create("http://stats:8082/v1/listen")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(listen))).build();
            var httpResponse = httpClientListen.send(httpRequestListen, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() != 200) {
                System.out.println("Listen stat failed");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean activateSong(Long id) {
        // activate song on catalogues

        var httpClient = HttpClient.newBuilder().build();
        //var httpRequest = HttpRequest.newBuilder(statsService.getUriBuilder().path("/v1/listen/most").build()).build();
        var httpRequest = HttpRequest.newBuilder(URI.create("http://catalogues:8081/v1/tracks/activate/" + id)).POST(HttpRequest.BodyPublishers.noBody()).build();
        try {
            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() != 200) {
                System.out.println("Activation failed");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }


        // send upload stat to stats
        var uploadDate = new UploadDate(id);
        try {
            var httpClientUpload = HttpClient.newBuilder().build();
            var httpRequestUpload = HttpRequest.newBuilder(URI.create("http://stats:8082/v1/upload")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(uploadDate))).build();
            var httpResponse = httpClientUpload.send(httpRequestUpload, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() != 200) {
                System.out.println("Upload stat failed");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
