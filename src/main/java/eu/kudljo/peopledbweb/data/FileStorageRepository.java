package eu.kudljo.peopledbweb.data;

import eu.kudljo.peopledbweb.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class FileStorageRepository {

    @Value("${STORAGE_FOLDER}")
    private String storageFolder;

    public void save(String originalFilename, InputStream inputStream) {
        try {
            Path filePath = getFilePath(originalFilename);
            Files.copy(inputStream, filePath);
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    public Resource findByName(String filename) {
        try {
            Path filePath = getFilePath(filename);
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new StorageException(e);
        }
    }

    private Path getFilePath(String filename) {
        return Path.of(storageFolder).resolve(filename).normalize();
    }

    public void deleteAllByName(Collection<String> fileNames) {
        try {
            Set<String> filteredFileNames = fileNames.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            for (String fileName : filteredFileNames) {
                Path filePath = getFilePath(fileName);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }
}
