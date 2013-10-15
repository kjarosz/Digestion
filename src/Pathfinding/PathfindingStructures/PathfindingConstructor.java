package Pathfinding.PathfindingStructures;

import java.awt.Rectangle;
import java.util.LinkedList;

import Pathfinding.PathfindingStructures.*;

public class PathfindingConstructor {
	private LinkedList<Platform> mGraph;

	private Platform mStartPlatform;
	private Platform mEndPlatform;
	private PlatformLink mLink;

	public PathfindingConstructor() {
		mGraph = new LinkedList<Platform>();

		mStartPlatform = null;
		mEndPlatform = null;
		mLink = null;
	}

	public void createPlatform(int id, int x, int y, int width, int height) {
		Platform platform = new Platform(new Rectangle(x, y, width, height), id);
		mGraph.add(platform);
	}

	public void beginLinkConstruct(int startID, int endID) {
		// Find start platform
		for(Platform platform: mGraph) {
			if(platform.getID() == startID) {
				mStartPlatform = platform;
				break;
			}
		}

		if(mStartPlatform == null)
			return;

		// Find endPlatform
		for(Platform platform: mGraph) {
			if(platform.getID() == endID) {
				mEndPlatform = platform;
				break;
			}
		}

		if(mEndPlatform == null) {
			mStartPlatform = null;
			return;
		}

		mLink = new PlatformLink(mStartPlatform, mEndPlatform);
	}

	public void endLinkConstruct() {
		if(mLink == null)
			return;

		mStartPlatform.addPlatformLink(mLink);
		mEndPlatform.addPlatformLink(mLink.invert());

		mStartPlatform = mEndPlatform = null;
		mLink = null;
	}

	public void createLinkCell(int x, int y, int width, int height) {
		if(mLink == null)
			return;

		mLink.addLinkCell(new Rectangle(x, y, width, height));
	}

	public LinkedList<Platform> getGraph() {
		return mGraph;
	}
}
