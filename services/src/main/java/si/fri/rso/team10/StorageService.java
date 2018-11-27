package si.fri.rso.team10;

import com.fasterxml.jackson.databind.ObjectMapper;
import si.fri.rso.team10.dto.ListenInstance;
import si.fri.rso.team10.dto.UploadDate;

import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

@RequestScoped
public class StorageService {

    public boolean recordStream(Long id) {
        var listen = new ListenInstance(id);
        try {
            var httpClientListen = HttpClient.newBuilder().build();
            var httpRequestListen = HttpRequest.newBuilder(URI.create("http://172.17.0.1:8082/v1/listen")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(listen))).build();
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
        var httpRequest = HttpRequest.newBuilder(URI.create("http://172.17.0.1:8081/v1/tracks/activate/" + id)).POST(HttpRequest.BodyPublishers.noBody()).build();
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
            var httpRequestUpload = HttpRequest.newBuilder(URI.create("http://172.17.0.1:8082/v1/upload")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(uploadDate))).build();
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
