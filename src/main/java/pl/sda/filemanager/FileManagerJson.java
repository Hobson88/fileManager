package pl.sda.filemanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileManagerJson implements FileManager {
    private Path metaDataFile;
    private FilesMetaData filesMetaData;
    private ObjectMapper objectMapper;



    public FileManagerJson(Path path) {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.metaDataFile = path;
        try {
            FilesMetaData defaultContent = new FilesMetaData();
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            if (fileIsInvalid(path))
                objectMapper.writeValue(metaDataFile.toFile(), defaultContent);
            filesMetaData = objectMapper.readValue(metaDataFile.toFile(), FilesMetaData.class);
        } catch (IOException e) {
            throw new FileManagerException("Can not read the json file.", e);
        }
    }

    private boolean fileIsInvalid(Path path) throws IOException {
        return Files.readAllLines(path).isEmpty();
    }

    @Override
    public void addTag(String tag, Path path) throws FileManagerException {
        boolean isFileNeverTagBefore = true;
        for (FileEntry fe : filesMetaData.getEntries()) {
            if (fe.getPath().equals(path.toString())) {
                fe.addTag(tag);
                isFileNeverTagBefore = false;
            }
        }
        if (isFileNeverTagBefore){
            filesMetaData.createEntry(tag, path);
        }

        try {
            objectMapper.writeValue(metaDataFile.toFile(), filesMetaData);
        } catch (IOException e) {
            throw new FileManagerException(String.format(
                    "Failed to add tag %s to file %s using metadata file %s",
                    tag, path, metaDataFile), e);
        }
    }

    @Override
    public List<Path> findFilesByTag(String tag) throws FileManagerException {
//        return filesMetaData.getEntries().stream().filter(fileEntry -> fileEntry.getTags().contains(tag))
//                .map(fileEntry -> Paths.get(fileEntry.getPath())).collect(Collectors.toList());
        List<Path> foundFilesByTag = new ArrayList<>();
        for (FileEntry fe : filesMetaData.getEntries()) {
            if (fe.getTags().contains(tag)) {
                foundFilesByTag.add(Paths.get(fe.getPath()));
            }
        }
        return foundFilesByTag;
    }

    @Override
    public void addTag(String tag, Path rootPath, String extension) throws FileManagerException {

        FileVisitor fileVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(extension)){
                    addTag(tag, file);
                }
                return  FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(rootPath, fileVisitor);
        } catch (IOException e) {
            throw new FileManagerException("Failed to traverse root file: "+rootPath, e);
        }
    }
}




















