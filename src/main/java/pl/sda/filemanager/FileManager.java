package pl.sda.filemanager;

import java.nio.file.Path;
import java.util.List;

/**
 * Interface to add tag to the file and find files by tag.
 */
public interface FileManager {
    /**
     * Tags a file with new tag.
     *
     * @param tag  a new tag
     * @param path a path to the file
     * @throws FileManagerException if filemanager failed
     */
    void addTag(String tag, Path path) throws FileManagerException;

    /**
     * Method to find files by tag.
     *
     * @param tag tag to find files
     * @return a list of all files having the given tag
     * @throws FileManagerException if filemanager failed
     */
    List<Path> findFilesByTag(String tag) throws FileManagerException;

    /**
     * Znajduje pliki z zadanym rozszerzeniem i przydziela im tag.
     *
     * @param tag tag, który przypiszemy plikom
     * @param rootPath ścieżka katalogu, w którym rozpoczynamy szukanie plików w sposób rekursywny
     * @param extension rozszerzenie, które chcemy znaleźć
     * @throws FileManagerException jeśli filemanager nie zadziała
     */
    void addTag(String tag, Path rootPath, String extension) throws FileManagerException;
}
