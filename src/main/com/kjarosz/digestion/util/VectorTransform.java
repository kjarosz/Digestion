package com.kjarosz.digestion.util;

@FunctionalInterface
public interface VectorTransform {
   public Vector2D filter(Vector2D vector);
}
