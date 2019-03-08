package pl.sda.filedownloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class FileExample {
    public static void main(String[] args) throws MalformedURLException {

        FileDownloader fileDownloader = new FileDownloaderURL();
        fileDownloader.download(new URL("https://www.w3.org/TR/PNG/iso_8859-1.txt"), Paths.get("text.txt"));

    }
}
