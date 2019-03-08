package pl.sda.filedownloader;

import java.net.URL;
import java.nio.file.Path;

public interface FileDownloader {
    void download(URL url, Path target) throws FileDownloaderException;
    boolean supports (URL url);
}