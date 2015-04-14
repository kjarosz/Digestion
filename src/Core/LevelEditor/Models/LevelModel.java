package Core.LevelEditor.Models;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

public class LevelModel extends AbstractModel {
   private String    name;
   private Dimension size;

   private LinkedList<Tile>          tiles;
   private LinkedList<Entity>        entities;
   private BufferedImage             background;
   
   public LevelModel() {
      tiles = new LinkedList<>();
      entities = new LinkedList<>();

      reset();
   }
   
   public void reset() {
      setName("New Level");
      setSize(new Dimension(1024, 768));
      clearTiles();
      clearEntities();
      
   }
   
   /* ********************************************************************** */
   /*                               ACCESSORS                                */
   /* ********************************************************************** */
   public String getName() {
      return name;
   }

   public void setName(String name) {
      firePropertyChangeEvent("name", this.name, name);
      this.name = name;
   }

   public Dimension getSize() {
      return new Dimension(size);
   }

   public void setSize(Dimension size) {
      firePropertyChangeEvent("size", this.size, size);
      this.size = size;
   }

   public LinkedList<Tile> getTiles() {
      return tiles;
   }

   public void addTiles(Tile tile) {
      if(!tileCollides(tile)) {
         firePropertyChangeEvent("tiles", null, tile);
         tiles.add(tile);
      }
   }
   
   private boolean tileCollides(Tile atile) {
      for(Tile tile: tiles) {
         if(tile.tileRect.intersects(atile.tileRect)) {
            return true;
         }
      }
      return false;
   }
   
   public void clearTiles() {
      firePropertyChangeEvent("tiles", null, null);
      tiles.clear();
   }
   
   public void removeTile(Tile tile) {
      firePropertyChangeEvent("tiles", tile, null);
      tiles.remove(tile);
   }
   
   public void removeTile(Point coords) {
      Iterator<Tile> it = tiles.iterator();
      while(it.hasNext()) {
         Tile tile = it.next();
         if(tile.tileRect.contains(coords)) {
            firePropertyChangeEvent("tiles", tile, null);
            it.remove();
         }
      }
   }

   public LinkedList<Entity> getEntities() {
      return entities;
   }

   public void addEntities(Entity entity) {
      firePropertyChangeEvent("entities", null, entity);
      entities.add(entity);
   }
   
   public void clearEntities() {
      firePropertyChangeEvent("entities", null, null);
      entities.clear();
   }
   
   public void removeEntity(Entity entity) {
      firePropertyChangeEvent("entities", entity, null);
      entities.remove(entity);
   }
   
   public void removeEntity(Point coords) {
      Iterator<Entity> it = entities.iterator();
      while(it.hasNext()) {
         Entity entity = it.next();
         if(entity.entityRect.contains(coords)) {
            firePropertyChangeEvent("entities", entity, null);
            it.remove();
         }
      }
   }

   public BufferedImage getBackground() {
      return background;
   }

   public void setBackground(BufferedImage background) {
      firePropertyChangeEvent("background", this.background, background);
      this.background = background;
   }
}
