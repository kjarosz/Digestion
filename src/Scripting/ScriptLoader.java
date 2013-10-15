package Scripting;

import Util.ErrorLog;
import java.io.File;
import java.util.LinkedList;

public class ScriptLoader {
   private final String NULL_SCRIPT_FILENAME = "NullScript.py";
   
   private File mNullScript;
	private LinkedList<Variable> mScriptList;
   
   public ScriptLoader(File directory, String scriptFilename) {
      mScriptList = new LinkedList<>();
      mNullScript = new File(directory, NULL_SCRIPT_FILENAME);
      loadScripts(directory, scriptFilename);
   }
	
   private void loadScripts(File directory, String scriptFilename) {
      try {
         findAndLoadScripts(directory, scriptFilename);
      } catch(InvalidDirectoryException ex) {
         ErrorLog errorLog = ErrorLog.getInstance();
         errorLog.writeError(ex.getLogMessage());
      }
   }
   
   private void findAndLoadScripts(File directory, String scriptFilename) {
      if(isDirectoryBad(directory))
         throw new InvalidDirectoryException(directory);
      
		LinkedList<File> directories = searchForDirectories(directory);
      LinkedList<File> directoriesWithScripts = findDirectoriesContainingScripts(directories, scriptFilename);
      saveScripts(directoriesWithScripts, scriptFilename);
	}
   
   private boolean isDirectoryBad(File directory) {
      return !directory.exists()
              ||
             !directory.isDirectory();
   } 
   
   private LinkedList<File> searchForDirectories(File directory) {
      LinkedList<File> directories = new LinkedList<>();
      for(File file: directory.listFiles())
         if(file.isDirectory())
            directories.add(file);
      return directories;
   }
   
   private LinkedList<File> findDirectoriesContainingScripts(LinkedList<File> directories, String scriptFilename) {
      LinkedList<File> directoriesWithScripts = new LinkedList<>();
      for(File directory: directories) {
         if(directoryContainsFileNamed(directory, scriptFilename))
            directoriesWithScripts.add(directory);
      }
      return directoriesWithScripts;
   }
   
   private boolean directoryContainsFileNamed(File directory, String filename) {
      for(File file: directory.listFiles()) {
         if(file.getName().equals(filename))
            return true;
      }
      return false;
   }
   
   private void saveScripts(LinkedList<File> scriptDirectories, String scriptFilename) {
      for(File directory: scriptDirectories) {
         String scriptName = directory.getName();
         Variable var = new Variable(scriptName);
         var.mValue = new File(directory, scriptFilename);
         mScriptList.add(var);
      }
   }
   
   public String[] getScriptNames() {
      String[] names = new String[mScriptList.size()];
      for(int i = 0; i < names.length; i++) {
         names[i] = mScriptList.get(i).mIdentifier;
      }
      return names;
   }
   
   public File findScriptByName(String scriptName) {
      for(Variable script: mScriptList) {
         if(script.mIdentifier.equals(scriptName)) {
            return (File)script.mValue;
         }
      }
      return mNullScript;
   }
}
