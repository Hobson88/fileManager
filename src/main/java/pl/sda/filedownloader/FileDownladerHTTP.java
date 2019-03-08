package pl.sda.filedownloader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDownladerHTTP implements FileDownloader {

    @Override
    public void download(URL url, Path target) throws FileDownloaderException {
//       jeśli wyłączymy przekierowania, trzeba obsługiwać ręcznie
//        HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpResponse response = httpClient.execute(new HttpGet(url.toURI()));
            Files.copy(response.getEntity().getContent(), target);

        } catch (IOException | URISyntaxException e) {
            throw new FileDownloaderException(String.format("Failed to download file from URL %s", url), e);
        }
        // -----------------   Ręczna obsługa przekierowań ------------------------------
/*
            try {
            HttpGet httpGet = new HttpGet(url.toURI());
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 301) {
                Header location = response.getFirstHeader("Location");
                String newUrl = location.getElements()[0].getName();
                HttpResponse fileResponse = httpClient.execute(new HttpGet(new URL(newUrl).toURI()));
                InputStream fileContent = fileResponse.getEntity().getContent();
                Files.copy(fileContent, target);
            }
        } catch (URISyntaxException e) {
            throw new FileDownloaderException(String.format("Failed to download file. Invalid URL: %s", url), e);
        } catch (IOException e) {
            throw new FileManagerException(String.format("IO error, when downloading file: %s", url), e);
        }*/

    }

    @Override
    public boolean supports(URL url) {
        String protocol = url.getProtocol();
        return "http".equals(protocol) || "https".equals(protocol);
    }
}
