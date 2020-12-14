package com.kjarosz.digestion.entity.components;

import com.kjarosz.digestion.util.Vector2D;

public class Movable {
	public Vector2D velocity;
	public Vector2D netForce;
	public double mass;
	public boolean ignoreGravity;
	public double terminalVelocity;
   
	public boolean canJump;
	public boolean canDoubleJump;
	
   public Movable() {
      velocity = new Vector2D(0.0, 0.0);
      netForce = new Vector2D(0.0, 0.0);
      mass = 1.0;
      ignoreGravity = false;
      terminalVelocity = Double.MAX_VALUE;
      
      canJump = false;
      canDoubleJump = false;
   }
}
