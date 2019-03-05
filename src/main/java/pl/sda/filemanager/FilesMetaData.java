package pl.sda.filemanager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesMetaData {
    private List<FileEntry> entries;
    private String version;

    public FilesMetaData() {
        entries = new ArrayList<>();
        version = "0.0.1";
    }

    public List<FileEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<FileEntry> entries) {
        this.entries = entries;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilesMetaData that = (FilesMetaData) o;
        return Objects.equals(entries, that.entries) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries, version);
    }

    @Override
    public String toString() {
        return "FilesMetaData{" +
                "entries=" + entries +
                ", version='" + version + '\'' +
                '}';
    }

    public void createEntry(String tag, Path path) {
        FileEntry fileEntry = new FileEntry();
        fileEntry.addTag(tag);
        fileEntry.setPath(path.toString());
        entries.add(fileEntry);
    }
}
