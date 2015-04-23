package Core.LevelEditor.Models;

import java.util.LinkedList;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class EntityModelList implements ListModel<EntityModel> {

   private LinkedList<ListDataListener> mDataListeners;
   
   private LinkedList<EntityModel> mEntityCompositions;
   
   public EntityModelList() {
      mDataListeners = new LinkedList<>();
      mEntityCompositions = new LinkedList<>();
   }
   
   @Override
   public void addListDataListener(ListDataListener listener) {
      if(!mDataListeners.contains(listener)) {
         mDataListeners.add(listener);
      }
   }
   
   public void addEntityModel(EntityModel model) {
      if(!mEntityCompositions.contains(model)) {
         mEntityCompositions.add(model);
         int index = mEntityCompositions.indexOf(model);
         fireIntervalAddedEvent(index);
      } else {
         throw new RuntimeException("Entity with this name already exists.");
      }
   }
   
   public void removeEntityModel(EntityModel model) {
      if(mEntityCompositions.contains(model)) {
         int index = mEntityCompositions.indexOf(model);
         mEntityCompositions.remove(model);
         fireIntervalRemovedEvent(index);
      } else {
         throw new RuntimeException("This entity is not in the list.");
      }
   }
   
   @Override
   public EntityModel getElementAt(int index) {
      return mEntityCompositions.get(index);
   }

   @Override
   public int getSize() {
      return mEntityCompositions.size();
   }

   @Override
   public void removeListDataListener(ListDataListener listener) {
      if(mDataListeners.contains(listener)) {
         mDataListeners.remove(listener);
      }
   }
   
   private void fireIntervalAddedEvent(int index) {
      ListDataEvent event = new ListDataEvent(this, 
            ListDataEvent.INTERVAL_ADDED, index, index);
      for(ListDataListener listener: mDataListeners) {
         SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               listener.intervalAdded(event);
            }
         });
      }
   }
   
   private void fireIntervalRemovedEvent(int index) {
      ListDataEvent event = new ListDataEvent(this,
            ListDataEvent.INTERVAL_REMOVED, index, index);
      for(ListDataListener listener: mDataListeners) {
         SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               listener.intervalRemoved(event);
            }
         });
      }
   }
}
