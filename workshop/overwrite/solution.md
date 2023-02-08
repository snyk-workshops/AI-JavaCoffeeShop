# Solution Fix for FREELOADER Assignment

In the `UploadController` we save the file using the `file.getOriginalFilename()`. This orginal filename comes directly from the HTTP request

There are multiple solutions to this problem

### Solution 1

Don't use the `file.getOriginalFilename()` at all. Instead generated your own unique filename for the picture.

### Solution 2

If you have or want to use the original filename, check the `CanonicalPath` to prevent path traversal outside of your intended upload folder.

```java
...
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, name);
        if (!fileNameAndPath.toFile().getCanonicalPath().startsWith(UPLOAD_DIRECTORY)) {
        throw new IOException("Could not upload file: " + name);
        }
        Files.write(fileNameAndPath, file.getBytes());
...

```