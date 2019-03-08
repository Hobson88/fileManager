package pl.sda.filedownloader;

public class FileDownloaderException extends RuntimeException {
    public FileDownloaderException(String message) {
        super(message);
    }

    public FileDownloaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
