package Core.LevelEditor.Utils;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Models.Tile;

public class LevelModelWriter extends SwingWorker<File, String> {
   
   private File mFile;
   private LevelModel mModel;
   
   public LevelModelWriter(File file, LevelModel model) {
      mFile = file;
      mModel = model;
   }
   
   @Override
   protected File doInBackground() {
      validateExtension();
      try {
         if(!mFile.exists()) {
            mFile.createNewFile();
         }
         write();
      } catch(IOException ex) {
         JOptionPane.showMessageDialog(null,
               "File could not be created.\n"+ex.getMessage(),
               "Error Creating File",
               JOptionPane.ERROR_MESSAGE);
         mFile = null;
      }
      return mFile;
   }
   
   private void validateExtension() {
      String name = mFile.getName();
      if(!name.endsWith(".json")) {
         mFile = new File(mFile.getParent(), name + ".json");
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
         mFile = null;
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
      JSONArray tiles = new JSONArray();
      for(Tile tile: mModel.getTiles()) {
         JSONObject jsonTile = new JSONObject();
         jsonTile.put("name", tile.name);
         jsonTile.put("x", tile.tileRect.x);
         jsonTile.put("y", tile.tileRect.y);
         jsonTile.put("width", tile.tileRect.width);
         jsonTile.put("height", tile.tileRect.height);
         
         tiles.add(jsonTile);
      }
      return tiles;
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
