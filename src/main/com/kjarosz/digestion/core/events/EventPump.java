package com.kjarosz.digestion.core.events;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
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

import javax.swing.SwingUtilities;

import com.kjarosz.digestion.core.events.KeyEvent.KeyAction;
import com.kjarosz.digestion.core.events.MouseEvent.MouseAction;
import com.kjarosz.digestion.core.events.MouseEvent.MouseButton;
import com.kjarosz.digestion.core.events.ScreenEvent.ScreenAction;


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
   
   private void addMouseEvent(MouseEvent event, MouseAction action) {
      MouseButton button = MouseButton.NONE;
      if(SwingUtilities.isLeftMouseButton(event)) {
         button = MouseButton.LEFT;
      } else if (SwingUtilities.isMiddleMouseButton(event)) {
         button = MouseButton.MIDDLE;
      } else if(SwingUtilities.isRightMouseButton(event)) {
         button = MouseButton.RIGHT;
      }
      Point position = event.getPoint();
      mEventQueue.add(new com.kjarosz.digestion.core.events.MouseEvent(button, action, position)); 
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      addMouseEvent(event, MouseAction.DRAG);
   }

   @Override
   public void mouseMoved(MouseEvent event) {
      addMouseEvent(event, MouseAction.MOVE);
   }

   @Override
   public void mouseClicked(MouseEvent event) {
      addMouseEvent(event, MouseAction.CLICKED);
   }

   @Override
   public void mouseEntered(MouseEvent event) {
      // Not sure if entered and dragged are useful
      // for anything
   }

   @Override
   public void mouseExited(MouseEvent event) { }

   @Override
   public void mousePressed(MouseEvent event) {
      addMouseEvent(event, MouseAction.PRESSED);
   }

   @Override
   public void mouseReleased(MouseEvent event) {
      addMouseEvent(event, MouseAction.RELEASED);
   }

   private void addKeyEvent(KeyEvent event, KeyAction action) {
      mEventQueue.add(new com.kjarosz.digestion.core.events.KeyEvent(action, event.getKeyCode()));
   }
   
   @Override
   public void keyPressed(KeyEvent event) {
      addKeyEvent(event, KeyAction.PRESSED);
   }

   @Override
   public void keyReleased(KeyEvent event) {
      addKeyEvent(event, KeyAction.RELEASED);
   }

   @Override
   public void keyTyped(KeyEvent event) {
      addKeyEvent(event, KeyAction.TYPED);
   }
}
