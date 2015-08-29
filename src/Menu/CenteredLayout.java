package Menu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CenteredLayout {
   private Dimension mParentSize;
   private LinkedList<Component> mComponents;

   private int mComponentGap;

   public CenteredLayout() {
      this(0);
   }

   public CenteredLayout(int gap) {
      mComponents = new LinkedList<>();
      mParentSize = new Dimension(0, 0);
      mComponentGap = gap;
   }

   public void addComponent(Component component) {
      mComponents.add(component);
      recalculateDimensions();
   }
   
   public void removeComponent(Component component) {
      try {
         mComponents.remove(component);
         recalculateDimensions();
      } catch(NoSuchElementException ex) {
         // Why is this exception thrown?
      }
   }

   public void resizeParent(Dimension newSize) {
      if(newSize.equals(mParentSize)) {
         return;
      } else {
         mParentSize = newSize;
         recalculateDimensions();
      }
   }

   private void recalculateDimensions() {
      Point parentCenter = findCenter(0, 0, mParentSize.width, mParentSize.height);
      Dimension totalOccupiedSpace = addUpOccupiedSpace();
      layComponentsOut(parentCenter, totalOccupiedSpace);
   }

   private Point findCenter(int x, int y, int width, int height) {
      return new Point( width/2 + x, height/2 + y );
   }

   private Dimension addUpOccupiedSpace() {
      int[] verticalSizes = new int[mComponents.size()];
      int[] horizontalSizes = new int[mComponents.size()];
      // Items will be stacked vertically 
      ListIterator<Component> it = mComponents.listIterator();
      while(it.hasNext()) {
         int index = it.nextIndex();
         Component comp = it.next();

         Dimension compSize = comp.getPreferredSize();
         verticalSizes[index] = compSize.height;
         horizontalSizes[index] = compSize.width;
      }

      Dimension occupiedSpace = new Dimension();
      occupiedSpace.width = max(horizontalSizes);
      occupiedSpace.height = sum(verticalSizes) + verticalSizes.length*mComponentGap;
      return occupiedSpace;
   }

   private int max(int array[]) {
      int max = Integer.MIN_VALUE;
      for(int item: array) {
         if (max < item) {
            max = item;
         }
      }
      return max;
   }

   private int sum(int array[]) {
      int total = 0;
      for(int item: array) {
         total += item;
      }
      return total;
   }

   private void layComponentsOut(Point center, Dimension bounds) {
      int x = center.x - bounds.width/2;
      int y = center.y - bounds.height/2;
      for(Component comp: mComponents) {
         Point position = new Point();
         position.x = x;
         position.y = y;

         Dimension size = new Dimension();
         size.width = bounds.width;
         size.height = comp.getPreferredSize().height;

         y += size.height + mComponentGap;

         comp.setLocation(position);
         comp.setSize(size);
      }
   }
}
