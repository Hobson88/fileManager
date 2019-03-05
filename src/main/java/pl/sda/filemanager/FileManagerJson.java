package pl.sda.filemanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManagerJson implements FileManager {
    private Path metaDataFile;
    private FilesMetaData filesMetaData;
    private ObjectMapper objectMapper;



    public FileManagerJson(Path path) {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.metaDataFile = path;
        try {
            if(!Files.exists(path)){
                FilesMetaData defaultContent = new FilesMetaData();
                Files.createFile(path);
                objectMapper.writeValue(metaDataFile.toFile(), defaultContent);
            }
            filesMetaData = objectMapper.readValue(metaDataFile.toFile(), FilesMetaData.class);
        } catch (IOException e) {
            throw new FileManagerException("Can not read the json file.", e);
        }
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
}
