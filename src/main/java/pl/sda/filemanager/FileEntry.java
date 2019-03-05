package pl.sda.filemanager;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FileEntry {
    private String path;
    private Set<String> tags;

    public FileEntry() {
        tags = new HashSet<>();
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void addTag (String tag){
        tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntry fileEntry = (FileEntry) o;
        return Objects.equals(path, fileEntry.path) &&
                Objects.equals(tags, fileEntry.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, tags);
    }

    @Override
    public String toString() {
        return "FileEntry{" +
                "path='" + path + '\'' +
                ", tags=" + tags +
                '}';
    }
}
