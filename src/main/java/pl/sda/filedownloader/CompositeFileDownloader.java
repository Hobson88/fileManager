package pl.sda.filedownloader;

import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CompositeFileDownloader implements FileDownloader {
    private List<FileDownloader> fileDownloaderList;

    public CompositeFileDownloader(FileDownloader... filedownloaders) {
        this.fileDownloaderList = Arrays.asList(filedownloaders);

    }

    @Override
    public void download(URL url, Path target) throws FileDownloaderException {
        findCorrectFileDownlader(url).ifPresent(f -> f.download(url, target));
    }

    @Override
    public boolean supports(URL url) {
        return findCorrectFileDownlader(url).isPresent();
    }

    private Optional<FileDownloader> findCorrectFileDownlader(URL url) {
        return fileDownloaderList.stream().filter(f -> f.supports(url)).findFirst();
    }
}
