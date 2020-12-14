package com.kjarosz.digestion.core.leveleditor.models;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.LinkedList;

public class LevelModel extends AbstractModel {
   private String    name;
   private Dimension size;

   private LinkedList<Entity>        entities;
   private BufferedImage             background;
   
   private PropertyChangeListener    entityListener;
   
   public LevelModel() {
      entities = new LinkedList<>();

      reset();
      
      createListeners();
   }
   
   public void reset() {
      setName("New Level");
      setSize(new Dimension(1024, 768));
      clearEntities();
   }
   
   private void createListeners() {
      entityListener = new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent e) {
            firePropertyChangeEvent("entities", e.getOldValue(), e.getNewValue());
         }
      };
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

   public LinkedList<Entity> getEntities() {
      return entities;
   }

   public Entity addEntity(Entity entity) {
      firePropertyChangeEvent("entities", null, entity);
      entity.addPropertyChangeListener(entityListener);
      entities.add(entity);
      return entity;
   }
   
   public void clearEntities() {
      firePropertyChangeEvent("entities", null, null);
      entities.clear();
   }
   
   public void removeEntity(Entity entity) {
      firePropertyChangeEvent("entities", entity, null);
      entities.remove(entity);
      entity.removePropertyChangeListener(entityListener);
   }
   
   public void removeEntity(Point coords) {
      Iterator<Entity> it = entities.iterator();
      while(it.hasNext()) {
         Entity entity = it.next();
         if(entity.getRect().contains(coords)) {
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
