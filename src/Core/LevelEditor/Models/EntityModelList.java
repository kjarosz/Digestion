package Core.LevelEditor.Models;

import java.util.LinkedList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class EntityModelList implements ListModel<EntityModel> {

   private LinkedList<ListDataListener> mDataListener;
   
   private LinkedList<EntityModel> mEntityCompositions;
   
   public EntityModelList() {
      mDataListener = new LinkedList<>();
      mEntityCompositions = new LinkedList<>();
   }
   
   @Override
   public void addListDataListener(ListDataListener listener) {
      if(!mDataListener.contains(listener)) {
         mDataListener.add(listener);
      }
   }

   @Override
   public EntityModel getElementAt(int index) {
      return mEntityCompositions.get(index);
   }

   @Override
   public int getSize() {
      
      return 0;
   }

   @Override
   public void removeListDataListener(ListDataListener listener) {
      if(mDataListener.contains(listener)) {
         mDataListener.remove(listener);
      }
   }
   
}
