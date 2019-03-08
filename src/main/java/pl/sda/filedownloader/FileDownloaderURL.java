package pl.sda.filedownloader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDownloaderURL implements FileDownloader {


    @Override
    public void download(URL url, Path target) throws FileDownloaderException {
        try(InputStream inputStream = url.openConnection().getInputStream()) {
            Files.copy(inputStream, target);
        } catch (IOException e) {
            throw new FileDownloaderException(
                    String.format("Nie można pobrać pliku z adresu URL: %s i zapisać w ścieżce: %s", url, target), e);
        }
    }

    @Override
    public boolean supports(URL url) {
        return true;
    }
}
