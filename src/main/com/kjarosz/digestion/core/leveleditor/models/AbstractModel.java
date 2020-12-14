package com.kjarosz.digestion.core.leveleditor.models;

/* ************************************************************************* */
/* This class contains utility functions for reporting when its properties   */
/* change. The subclass should declare all of its members private,           */
/* preferably immutable, and fire PropertyChangeEvents where appropriate.    */
/* ************************************************************************* */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

public abstract class AbstractModel {
   private LinkedList<PropertyChangeListener> propertyListeners = new LinkedList<>();
   
   public AbstractModel() {
      propertyListeners = new LinkedList<>();
   }
   
   public void addPropertyChangeListener(PropertyChangeListener listener) {
      if(!propertyListeners.contains(listener))
         propertyListeners.add(listener);
   }
   
   public void removePropertyChangeListener(PropertyChangeListener listener) {
      if(propertyListeners.contains(listener))
         propertyListeners.remove(listener);
   }
   
   protected void firePropertyChangeEvent(String property, Object oldVal, Object value) {
      for(PropertyChangeListener listener: propertyListeners) {
         PropertyChangeEvent e = new PropertyChangeEvent(
               this, property, oldVal, value);
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               listener.propertyChange(e);
            }
         });
      }
   }
}
