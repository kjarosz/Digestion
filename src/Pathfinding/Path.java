package Pathfinding;

import java.awt.geom.Rectangle2D;
import java.awt.Point;
import java.util.LinkedList;

import Pathfinding.PathfindingStructures.Platform;

public class Path {
	private LinkedList<Platform> mPath;
	
	public Path() {
		
	}
	
	public Path(Path copy) {
		
	}
	
	LinkedList<Platform> getPathReference() {
		return mPath;
	}
	
	public boolean arrivedOnTargetPlatform(Platform occupiedPlatform) {
		if(occupiedPlatform.getID() == mPath.getLast().getID())
			return true;
		
		return false;
	}
	
	public Point getNextCheckpoint(Rectangle2D.Float position, Platform occupiedPlatform) {
		return null;
	}
}
