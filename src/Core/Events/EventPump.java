package Core.Events;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ConcurrentLinkedQueue;

import Core.Events.ScreenEvent.ScreenAction;

public class EventPump implements KeyListener, MouseListener, MouseMotionListener, FocusListener, ComponentListener {
   private ConcurrentLinkedQueue<Event> mEventQueue;
   
   public EventPump() {
      mEventQueue = new ConcurrentLinkedQueue<>();
   }
   
   public ConcurrentLinkedQueue<Event> getQueue() {
      return mEventQueue;
   }
   
   private void addScreenEvent(ComponentEvent event, ScreenAction action) {
      Component component = event.getComponent();
      Dimension screenSize = component.getSize();
      mEventQueue.add(new ScreenEvent(action, screenSize));
   }
   
   private void addScreenEvent(FocusEvent event, ScreenAction action) {
      Component component = event.getComponent();
      Dimension screenSize = component.getSize();
      mEventQueue.add(new ScreenEvent(action, screenSize));
   }
   
   @Override
   public void componentHidden(ComponentEvent event) {
      addScreenEvent(event, ScreenAction.HIDDEN);
   }
   
   @Override
   public void componentMoved(ComponentEvent event) {
      // Swallow these. They're silly.
   }
   
   @Override
   public void componentResized(ComponentEvent event) {
      addScreenEvent(event, ScreenAction.RESIZED);
   }
   
   @Override
   public void componentShown(ComponentEvent event) {
      addScreenEvent(event, ScreenAction.SHOWN);
   }

   @Override
   public void focusGained(FocusEvent event) {
      addScreenEvent(event, ScreenAction.FOCUS_GAINED);
   }

   @Override
   public void focusLost(FocusEvent event) {
      addScreenEvent(event, ScreenAction.FOCUS_LOST);
   }

   @Override
   public void mouseDragged(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseMoved(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mousePressed(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseReleased(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void keyPressed(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void keyReleased(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void keyTyped(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }
}
