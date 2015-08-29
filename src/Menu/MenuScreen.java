package Menu;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import Core.Events.Callback;
import Core.Events.Event;
import Core.Events.ScreenEvent;
import Core.Game.Game;
import Core.Game.GameState;
import Graphics.CanvasInterface;
import Menu.Widgets.Button;
import Menu.Widgets.UIElement;

public abstract class MenuScreen implements GameState {
   protected Game mGame;
   
   private CenteredLayout mLayout;
   private LinkedList<UIElement> mElements;
   
   private BufferedImage mBackground;
   
   protected Dimension mScreenSize;

   public MenuScreen(Game game) {
      mGame = game;
      
      mLayout = new CenteredLayout(5);
      mElements = new LinkedList<>();
   }
   
   protected void loadBackground(String filename) {
      try {
         File bckgrFile = new File(filename);
         mBackground = ImageIO.read(bckgrFile);
      } catch(IOException ex) {
		   // There's nothing we can really do about this.
		   // And it's already going to be visible as it is.
      }
   }
   
   protected void createButton(String buttonImage, Dimension buttonSize, 
                               Callback callback) {
      Button button = new Button(buttonImage);
      button.setPreferredSize(buttonSize);
      button.setActionCallback(callback);
      mLayout.addComponent(button);
      mElements.add(button);
   }

   @Override
   public void beforeSwitch(Dimension screenSize) {
      changeParentSize(screenSize);
   }
   
   private void changeParentSize(Dimension parentSize) {
      mScreenSize = parentSize;
      mLayout.resizeParent(parentSize);
   }

   @Override
   public void handleEvents(Queue<Event> eventQueue) { 
      while(!eventQueue.isEmpty()) {
         Event event = eventQueue.poll();
         processEvent(event);
         mElements.forEach((e) -> e.handleUIEvent(event));
      }
   }
   
   private void processEvent(Event event) {
      if(event instanceof ScreenEvent) {
         ScreenEvent screen = (ScreenEvent)event;
         if(screen.mAction == ScreenEvent.ScreenAction.RESIZED) {
            changeParentSize(screen.mScreenSize);
         }
      }
   }

   @Override
   public void update() {}

   @Override
   public void draw(CanvasInterface canvas) {
      drawBackground(canvas);
      
      for(UIElement e: mElements) {
         e.draw(canvas);
      }
   }
   
   private void drawBackground(CanvasInterface canvas) {
      if(mBackground != null) {
         canvas.drawImage(mBackground, 0.0f, 0.0f, 0.0f, 
               (float)mScreenSize.width, 
               (float)mScreenSize.height);
      } else {
      }
   }

   @Override
   public void onSwitch() { }

}
