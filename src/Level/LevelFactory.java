package Level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jbox2d.common.Vec2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Entity.EntityComponents;
import Entity.EntityFactory;
import Util.UnitConverter;


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
      Object[] scriptNames = Arrays
           .stream(directory.listFiles())
           .filter((file) -> file.isDirectory() && hasLevelScript(file))
           .map((levelDir) -> levelDir.getName())
           .toArray();
      return Arrays.copyOf(scriptNames, scriptNames.length, String[].class);
   }
   
   public static Level loadLevel(String scriptname) {
      File levelScript = new File(LEVEL_DIRECTORY  
           + File.separator + scriptname 
           + File.separator + LEVEL_SCRIPTNAME);
      try(FileReader scriptReader = new FileReader(levelScript)) {
         return readLevel(scriptReader);
      } catch(Exception ex) {
         JOptionPane.showMessageDialog(
               null,
               "Level failed to load: \n"+ex.getMessage(),
               "Level Loading Failure",
               JOptionPane.ERROR_MESSAGE);
         return null;
      }
   }
   
   private static Level readLevel(Reader reader) throws IOException, ParseException {
      JSONParser parser = new JSONParser();
      JSONObject levelDescriptor = (JSONObject)parser.parse(reader);
      String levelName = (String)levelDescriptor.get("name");
      Vec2 levelSize = getSize(levelDescriptor);
      Level level = new Level(levelName, levelSize);
      loadLevelEntities(levelDescriptor, level);
      return level;
   }
   
   private static float getFloat(JSONObject src, String id) {
      return ((Number)src.get(id)).floatValue();
   }
   
   private static Vec2 getPosition(JSONObject descriptor) {
      Vec2 position = new Vec2(
            getFloat(descriptor, "x"),
            getFloat(descriptor, "y")
            );
      return UnitConverter.pixelsToMeters(position);
   }
   
   private static Vec2 getSize(JSONObject descriptor) {
      Vec2 size = new Vec2(
            getFloat(descriptor, "width"), 
            getFloat(descriptor, "height")
         );
      return UnitConverter.pixelsToMeters(size);
   }
   
   private static void loadLevelEntities(JSONObject descriptor, Level level) {
      EntityFactory factory = EntityFactory.getInstance();
      EntityContainer container = level.entityContainer;
      for(JSONObject entityDesc: jsonify((JSONArray)descriptor.get("tiles"))) {
         int entityID = container.createNewEntity();
         EntityComponents components = container.accessComponents(entityID);
         Vec2 position = getPosition(entityDesc);
         Vec2 size = getSize(entityDesc);
         position.addLocal(size.mul(0.5f));
         int mask = factory.createEntity(level.world, 
               (String)entityDesc.get("name"),
               position,
               size,
               components);
         container.setEntityMask(entityID, mask);
      }
   }

   private static List<JSONObject> jsonify(JSONArray array) {
      List<JSONObject> objects = new LinkedList<>();
      for(Object object: array) {
         objects.add((JSONObject)object);
      }
      return objects;
   }
}
