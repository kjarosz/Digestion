package com.kjarosz.digestion.menu.widgets;

import com.kjarosz.digestion.core.events.Event;
import com.kjarosz.digestion.graphics.CanvasInterface;

public interface UIElement {
   public void handleUIEvent(Event event);
   public void draw(CanvasInterface canvas);
}
