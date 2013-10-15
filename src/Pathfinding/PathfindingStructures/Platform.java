package Pathfinding.PathfindingStructures;

import java.awt.Rectangle;
import java.util.LinkedList;

public class Platform {
	public static int sLastID = 1;
	
	private int mID;
	private Rectangle mDimensions;
	private LinkedList<PlatformLink> mLinkedPlatforms;
	
	public Platform(Rectangle dimensions) {
		mID = sLastID++;
		mDimensions = dimensions;
		mLinkedPlatforms = new LinkedList<PlatformLink>();
	}
	
	public Platform(Rectangle dimensions, int ID) {
		mDimensions = dimensions;
		mID = ID;
		mLinkedPlatforms = new LinkedList<PlatformLink>();
	}
	
	public int getID() { 
		return mID; 
	}
	
	public Rectangle getDimensions() {
		return mDimensions;
	}
	
	public LinkedList<PlatformLink> getPlatformLinks() {
		return mLinkedPlatforms;
	}
	
	public void addPlatformLink(PlatformLink link) {
		if(link != null)
			mLinkedPlatforms.add(link);
	}
	
	public boolean hasLink(int ID) {
		if(mLinkedPlatforms == null) {
			System.out.println("Linked platforms null");
			return false;
		}
		
		for(int i = 0; i < mLinkedPlatforms.size(); i++) {
			if(mLinkedPlatforms.get(i) == null) {
				System.out.println("Link is null");
				
			}
			
			if(mLinkedPlatforms.get(i).getGoalPlatform() == null) {
				System.out.println("Goal plat is null");
			}
			if(mLinkedPlatforms.get(i).getGoalPlatform().getID() == ID)
				return true;
		}
		
		return false;
	}
}