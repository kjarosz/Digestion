package Core.LevelEditor.Utils;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Core.LevelEditor.Models.Entity;
import Core.LevelEditor.Models.EntityCache;
import Core.LevelEditor.Models.LevelModel;

public class LevelModelReader extends SwingWorker<LevelModel, String> {
   private File mFile;
   private LevelModel mLevelModel;

   public LevelModelReader(File file) {
      mFile = file;
      mLevelModel = new LevelModel();
   }

   @Override
   protected LevelModel doInBackground() {
      try {
         attemptRead();
         return mLevelModel;
      } catch(Exception ex) {
         return null;
      }
   }
   
   private void attemptRead() {
      try(FileReader reader = new FileReader(mFile)) {
         read(reader);
      } catch (FileNotFoundException ex) {
         JOptionPane.showMessageDialog(null, 
               "File could not be found.",
               "File Not Found",
               JOptionPane.ERROR_MESSAGE);
         throw new RuntimeException("File does not exist.");
      } catch (IOException ex) {
         JOptionPane.showMessageDialog(null, 
               "File failed be read.",
               "File Close Error",
               JOptionPane.ERROR_MESSAGE);
      } catch(ParseException ex) {
         JOptionPane.showMessageDialog(null,
               "File does not contain a valid level description.",
               "Invalid Level Script",
               JOptionPane.ERROR_MESSAGE);
      }
   }
   
   private void read(Reader input) throws ParseException, IOException {
      JSONParser tokener = new JSONParser();
      JSONObject level = (JSONObject)tokener.parse(input);
      mLevelModel.setName((String)level.get("name"));
      mLevelModel.setSize(new Dimension(
            (int)level.get("width"),
            (int)level.get("height")
         ));
      JSONArray tiles = (JSONArray)level.get("tiles");
      for(Entity entity: getEntities(tiles)) {
         mLevelModel.addEntity(entity);
      }
   }
   
   private LinkedList<Entity> getEntities(JSONArray tiles) {
      EntityCache cache = new EntityCache();
      LinkedList<Entity> entities = new LinkedList<>();
      for(JSONObject entityDesc: jsonify(tiles)) {
         String entityName = (String)entityDesc.get("name");
         Entity entity = cache.cloneEntity(entityName);
         Rectangle entityRect = new Rectangle();
         entityRect.x = (int)entityDesc.get("x");
         entityRect.y = (int)entityDesc.get("y");
         entityRect.width = (int)entityDesc.get("width");
         entityRect.height = (int)entityDesc.get("height");
         entity.setRect(entityRect);
         entities.add(entity);
      }
      return entities;
   }
   
   private List<JSONObject> jsonify(JSONArray array) {
      List<JSONObject> objects = new LinkedList<>();
      for(Object object: array) {
         objects.add((JSONObject)object);
      }
      return objects;
   }
}
