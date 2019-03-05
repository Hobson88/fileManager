package pl.sda.filemanager;

import java.nio.file.Paths;

public class CliController {
    public static void main(String[] args) {
        FileManager fileManager = createFileManager();

        if (args[0].equals("addtag")) {
            fileManager.addTag(args[1], Paths.get(args[2]));
        }
        if (args[0].equals("find")){
            fileManager.findFilesByTag(args[1]).forEach(System.out::println);
        }
    }


    private static FileManager createFileManager() {
        FileManager fileManager = new FileManagerJson(Paths.get("db.json"));
        return fileManager;
    }
}
