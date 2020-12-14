package com.kjarosz.digestion.util;

public class FileExistsException extends RuntimeException {
   public FileExistsException(String name) {
      super("File name \"" + name + "\" already exists.");
   }
}
