package pl.sda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sda.filemanager.FileManager;
import pl.sda.filemanager.FileManagerJson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileManagerJsonTest {
    // @formatter:off
    @DisplayName(
            "Kiedy dodaję taga music do pliku test-dir\\test-file.mp3, " +
            "gdy plik z metadanymi jeszcze nie istnieje, " +
            "to po wyszukaniu po tym tagu dostaje wylacznie plik test-file.mp3"
    )
    // @formatter:on
    @Test
    void test0() throws Exception {
        //given
        Path metaData = Files.createTempFile("metaData", ".json");
        FileManager fileManager = new FileManagerJson(metaData);
        Path testPath = Paths.get("test-dir", "test-file.mp3");
        String testTag = "music";

        //when
        fileManager.addTag(testTag, testPath);

        //then
        List<Path> matchingFiles = fileManager.findFilesByTag(testTag);
        assertThat(matchingFiles).containsOnly(testPath);
    }

    // @formatter:off
    @DisplayName(
            "Kiedy plik z metadanymi juz istnieje i ma 2 wpisy, " +
            "ktore zawieraja odpowiednio tagi audio oraz video, " +
            "to gdy wyszukamy po tagu video, to znajdziemy tylko ten wpis z video"
    )
    // @formatter:on
    @Test
    void test1() throws Exception {
        // given
        Path existingFile = metdataWithAudioAndVideoTags();

        // when
        FileManager fileManager = new FileManagerJson(existingFile);

        // then
        List<Path> matchingFiles = fileManager.findFilesByTag("video");
        assertThat(matchingFiles).containsOnly(Paths.get("test1.mp4"));
    }

    private Path metdataWithAudioAndVideoTags() throws IOException {
        Path metadata = Files.createTempFile("metadata", ".json");
        InputStream exampleMetadata = getClass().getResourceAsStream("/audio-and-video-metadata.json");
        Files.copy(exampleMetadata, metadata, StandardCopyOption.REPLACE_EXISTING);
        return metadata;
    }
}
