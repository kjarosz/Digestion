package Level;

import Scripting.ScriptFactory;
import Scripting.ScriptLoader;
import Scripting.ScriptObject;
import Util.ErrorLog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class LevelLoaderFactory {
   private final String DEFAULT_DIRECTORY = "resources" + File.separator + "Levels";
   private final String LEVEL_FILENAME = "LevelLoader.py";
   private final String LEVEL_CLASSNAME = "LevelLoader";
   
   private ScriptLoader mScriptLoader;
   private ScriptFactory mScriptFactory;
   
   public LevelLoaderFactory() {
      mScriptLoader = new ScriptLoader(new File(DEFAULT_DIRECTORY), LEVEL_FILENAME);
      mScriptFactory = new ScriptFactory();
   }
   
   public String[] getLevelScripts() {
      return mScriptLoader.getScriptNames();
   }
   
   public LevelLoadingScript loadLevelScript(String scriptName) {
      File scriptFile = mScriptLoader.findScriptByName(scriptName);
      ScriptObject scriptObject = mScriptFactory.createScriptInstance(scriptFile, LEVEL_CLASSNAME, LevelLoadingScript.class);
      return (LevelLoadingScript)scriptObject.script;
   }
   
   public void saveLevel(Level level, EntityContainer world) {
      try (BufferedWriter writer = getWriter(level)) {
         LevelLoadingScriptGenerator.generateScript(level, world, writer);
      } catch(IOException | MissingNameException ex) {
         ErrorLog errorLog = ErrorLog.getInstance();
         errorLog.writeError(ex.getMessage());
      }
   }
   
   private BufferedWriter getWriter(Level level) throws IOException {
      File levelDirectory = new File(DEFAULT_DIRECTORY + File.separator + getLevelName(level));
      if(!levelDirectory.exists())
         levelDirectory.mkdir();
      
      File levelFile = new File(levelDirectory, LEVEL_FILENAME);
      if(!levelFile.exists())
         levelFile.createNewFile();
      
      return new BufferedWriter(new FileWriter(levelFile));
   }
   
   private String getLevelName(Level level) {
      if(!level.name.isEmpty())
         return level.name;
      
      String name = (String)JOptionPane.showInputDialog("Enter the name of the level:");               
      if(name.isEmpty()) {
         JOptionPane.showMessageDialog(null, "Names cannot be empty");
         throw new MissingNameException("Empty name found.");
      }
      
      level.name = name;
      return name;
   }
}
