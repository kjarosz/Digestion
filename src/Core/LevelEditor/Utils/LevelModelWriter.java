package Core.LevelEditor.Utils;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Core.LevelEditor.Models.Entity;
import Core.LevelEditor.Models.LevelModel;

public class LevelModelWriter extends SwingWorker<File, String> {
   
   private File mFile;
   private LevelModel mModel;
   
   public LevelModelWriter(File file, LevelModel model) {
      mFile = file;
      mModel = model;
   }
   
   @Override
   protected File doInBackground() {
      try {
         attemptWriting();
         return mFile;
      } catch(Exception ex) {
         return null;
      }
   }
   
   private void attemptWriting() throws Exception {
      validateExtension();
      readyFile();
      write();
   }
   
   private void validateExtension() {
      String name = mFile.getName();
      if(!name.endsWith(".json")) {
         mFile = new File(mFile.getParent(), name + ".json");
      }
   }
   
   private void readyFile() throws IOException {
      if(mFile.exists()) {
         return;
      }

      try {
         mFile.createNewFile();
      } catch(IOException ex) {
         JOptionPane.showMessageDialog(null,
               "File could not be created.\n"+ex.getMessage(),
               "Error Creating File",
               JOptionPane.ERROR_MESSAGE);
         throw ex;
      }
   }
   
   private void deleteCreatedFile() {
      if(mFile.exists()) {
         mFile.delete();
      }
   }
   
   private void write() {
      try (FileWriter fWriter = new FileWriter(mFile);
           BufferedWriter writer = new BufferedWriter(fWriter)) 
      {
         writeLevel(writer);
      } 
      catch(IOException ex) 
      {
         JOptionPane.showMessageDialog(null,
               "Failed to write level.\n"+ex.getMessage(),
               "Error Writing File",
               JOptionPane.ERROR_MESSAGE);
         deleteCreatedFile();
      }
   }
   
   @SuppressWarnings("unchecked")
   private void writeLevel(BufferedWriter writer) throws IOException {
      JSONObject level = new JSONObject();
      level.put("name", mModel.getName());
      
      Dimension size = mModel.getSize();
      level.put("width", size.width);
      level.put("height", size.height);
      
      level.put("tiles", getTileJson());
      
      writer.write(level.toString());
   }

   @SuppressWarnings("unchecked")
   private JSONArray getTileJson() {
      JSONArray entities = new JSONArray();
      for(Entity entity: mModel.getEntities()) {
         JSONObject jsonTile = new JSONObject();
         jsonTile.put("name", entity.getName());
         
         Rectangle bounds = entity.getRect();
         jsonTile.put("x", bounds.x);
         jsonTile.put("y", bounds.y);
         jsonTile.put("width", bounds.width);
         jsonTile.put("height", bounds.height);
         
         entities.add(jsonTile);
      }
      return entities;
   }
   
   @Override
   protected void done() {
      try {
         if(!isCancelled() && get() != null)
            JOptionPane.showMessageDialog(null, "Level saved.");
      } catch (HeadlessException | InterruptedException | ExecutionException e) {
         JOptionPane.showMessageDialog(null, 
               "Some shit went down.\n"+e.getMessage(),
               "Unexpected failure.", 
               JOptionPane.ERROR_MESSAGE);
      }
   }
}
