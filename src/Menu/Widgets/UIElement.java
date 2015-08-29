package Menu.Widgets;

import Core.Events.Event;
import Graphics.CanvasInterface;

public interface UIElement {
   public void handleUIEvent(Event event);
   public void draw(CanvasInterface canvas);
}
