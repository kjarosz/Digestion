package com.kjarosz.digestion.entity.components;

import java.awt.image.BufferedImage;

import com.kjarosz.digestion.entity.systems.DrawingSystem;

public class Drawable {
   public BufferedImage image;
   public boolean tiled;
   public boolean flipped;
   
   public Drawable() {
      image = DrawingSystem.getNullImage();
      tiled = false;
      flipped = false;
   }
}
