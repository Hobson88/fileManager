package pl.sda.jackson.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import pl.sda.filemanager.FilesMetaData;

import java.io.IOException;
import java.nio.file.Paths;

public class FileMetaDataExample {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        FilesMetaData filesMetaData = objectMapper.readValue(Paths.get("metadata.json").toFile(), FilesMetaData.class);
        System.out.println(filesMetaData);

        objectMapper.writeValue(Paths.get("metadaOut.json").toFile(), filesMetaData);

    }
}
