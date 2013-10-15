package Util;

public class FileExistsException extends RuntimeException {
   public FileExistsException(String name) {
      super("File name \"" + name + "\" already exists.");
   }
}
