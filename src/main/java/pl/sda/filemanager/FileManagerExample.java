package pl.sda.filemanager;

import java.nio.file.Paths;

public class FileManagerExample {
    public static void main(String[] args) {

        FileManager fileManager = new FileManagerJson(Paths.get("metadata-test.json"));
        System.out.println(fileManager.findFilesByTag("document"));
        System.out.println(fileManager.findFilesByTag("video"));

        fileManager.addTag("Science",Paths.get("D:","test4.txt"));
        System.out.println(fileManager.findFilesByTag("disco"));


    }
}
