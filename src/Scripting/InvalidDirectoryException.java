/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Scripting;

import java.io.File;

public class InvalidDirectoryException extends RuntimeException {
   private File mDirectory;
   
   public InvalidDirectoryException(File directory) {
      super("The given directory is invalid.");
      mDirectory = directory;
   }
   
   public String getLogMessage() {
      StringBuilder message = new StringBuilder();
      message.append(getMessage()).append("\n");
      message.append("File: ").append(mDirectory.getPath()).append("\n");
      message.append(this.getStackTrace());
      return message.toString();
   }
}
