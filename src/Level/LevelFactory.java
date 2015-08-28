package Level;

import java.io.File;
import java.util.Arrays;


public abstract class LevelFactory {
   private static final String LEVEL_DIRECTORY = "resources/Levels/";
   private static final String LEVEL_SCRIPTNAME = "Level.json";

   private static boolean hasLevelScript(File directory) {
      for(File file: directory.listFiles()) {
         if(file.getName().equals(LEVEL_SCRIPTNAME)) {
            return true;
         }
      }
      return false;
   }
   
   public static String[] getLevelScripts() {
      File directory = new File(LEVEL_DIRECTORY);
      return (String[])Arrays
           .stream(directory.listFiles())
           .filter((file) -> file.isDirectory() && hasLevelScript(file))
           .map((levelDir) -> levelDir.getName())
           .toArray();
   }
   
   public static Level loadLevel(String scriptname) {
      File levelScript = new File(LEVEL_DIRECTORY  
           + File.separator + scriptname 
           + File.separator + LEVEL_SCRIPTNAME);
      Level level = new Level();
      return level;
   }
}
