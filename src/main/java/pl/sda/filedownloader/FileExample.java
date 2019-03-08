package pl.sda.filedownloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class FileExample {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://www.w3.org/TR/PNG/iso_8859-1.txt");

        FileDownloader fileDownlader = new CompositeFileDownloader(new FileDownladerHTTP(), new FileDownloaderURL());

        boolean supports = fileDownlader.supports(url);
        System.out.println(supports);
        fileDownlader.download(url, Paths.get("test.txt"));

//        FileDownloader fileDownloader = new FileDownladerHTTP();
//          fileDownloader.download(new URL("https://www.w3.org/TR/PNG/iso_8859-1.txt"), Paths.get("text.txt"));
//        URL url = new URL("http://ccrma.stanford.edu/~jos/mp3/viola2.mp3");
//        boolean isProtocolSupported = fileDownloader.supports(url);
//        System.out.println("File downloader supports this protocol: " + isProtocolSupported);
//        if (isProtocolSupported)
//            fileDownloader.download(url, Paths.get("test.mp3"));
//    }
    }
}
