package strategy;
// Define the strategy interface
interface PathStorageStrategy {
    // No methods needed for this example
}

// Concrete strategy for TriePathStorage
class TriePathStorageStrategy implements PathStorageStrategy {
    // No additional methods needed for this example
}

// Context class that uses the strategy
class FileSystem {
    private PathStorageStrategy storageStrategy;

    public FileSystem(PathStorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public void ls() {
        System.out.println("Listing files and directories");
    }

    public void mkdir(String directoryName) {
        System.out.println("Creating directory " + directoryName);
    }

    public void get_content_from_file(String filePath) {
        System.out.println("Getting content from file " + filePath);
    }

    public void add_content_to_file(String filePath, String content) {
        System.out.println("Adding content to file " + filePath);
    }

    public void navigate(String path, boolean createDirIfMissing) {
        System.out.println("Navigate to path " + path + ", createDirIfMissing=" + createDirIfMissing);
    }
}

// Example usage
public class Main {
    public static void main(String[] args) {
        // Create context with TriePathStorageStrategy
        FileSystem fs = new FileSystem(new TriePathStorageStrategy());

        // Example usage of the methods
        fs.ls();
        fs.mkdir("/home/user/documents");
        fs.get_content_from_file("/home/user/documents/file.txt");
        fs.add_content_to_file("/home/user/documents/file.txt", "New content");
        fs.navigate("/home/user/documents", true);
        fs.ls();
    }
}
