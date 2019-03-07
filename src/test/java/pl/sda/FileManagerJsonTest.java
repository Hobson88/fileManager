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

    @DisplayName(
            "Mając podaną ścieżkę do katalogu zawierającego pliki mp3,"
                    + "chcemy przypisać wszystkim znalezionym plikom tag music"
    )
    @Test
    void test2() throws Exception {
        //given
        Path tempDB = Files.createTempFile("DB_", ".json");
        FileManager fileManager = new FileManagerJson(tempDB);
        // create test directory structure
        // test-work-dir-<hash>
        // |_test0.mp3
        // |_test1.mp4
        // |_dir-0
        // |__test2.mp3
        // |__test3.txt
        Path rootDir = Files.createTempDirectory("test-work-dir");
        Path test0Mp3File = Files.createFile(rootDir.resolve("test0.mp3"));
        Files.createFile(rootDir.resolve("test1.mp4"));
        Path dir0 = Files.createDirectory(rootDir.resolve("dir-0"));
        Path test2Mp3File = Files.createFile(dir0.resolve("test2.mp3"));
        Files.createFile(dir0.resolve("test3.txt"));

        //when
        fileManager.addTag("music", rootDir, ".mp3");

        //then
        List<Path> allMusicFiles = fileManager.findFilesByTag("music");
        assertThat(allMusicFiles).containsExactlyInAnyOrder(test0Mp3File, test2Mp3File);
    }

    private Path metdataWithAudioAndVideoTags() throws IOException {
        Path metadata = Files.createTempFile("metadata", ".json");
        InputStream exampleMetadata = getClass().getResourceAsStream("/audio-and-video-metadata.json");
        Files.copy(exampleMetadata, metadata, StandardCopyOption.REPLACE_EXISTING);
        return metadata;
    }


}
