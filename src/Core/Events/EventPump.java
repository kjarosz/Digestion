package Core.Events;

import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventPump implements KeyListener, MouseListener, MouseMotionListener, FocusListener {
   private ConcurrentLinkedQueue<Event> mEventQueue;
   
   public EventPump() {
      mEventQueue = new ConcurrentLinkedQueue<>();
   }
   
   public ConcurrentLinkedQueue<Event> getQueue() {
      return mEventQueue;
   }
}
