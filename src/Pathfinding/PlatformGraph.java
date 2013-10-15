package Pathfinding;

import Pathfinding.PathfindingStructures.PathfindingConstructor;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;

import org.python.core.PyJavaType;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import Pathfinding.PathfindingStructures.*;

public class PlatformGraph {
	private LinkedList<Platform> mPathmap;
	
	private Rectangle2D.Float mTarget;
	private Platform mTargetPlatform;

	public PlatformGraph() {
		mPathmap = new LinkedList<Platform>();
		
		mTarget = null;
		mTargetPlatform = null;
	}
	
	public boolean loadPlatformGraph(String filename) {
		if(filename == null || filename.isEmpty())
			return false;
		
		File pathfindingScript = new File(filename);
		if(!pathfindingScript.exists() || !pathfindingScript.isFile())
			return false;
		
		PathfindingConstructor constructor = new PathfindingConstructor();
		
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.execfile(pathfindingScript.getPath());
		
		PyObject constructorFunc = interpreter.get("ConstructPlatformGraph");
		constructorFunc.__call__(PyJavaType.wrapJavaObject(constructor));
		
		LinkedList<Platform> graph = constructor.getGraph();
		
		if(graph == null || graph.isEmpty())
			return false;
		
		mPathmap = graph;
		
		return true;
	}
	
	public Platform findIntersectingPlatform(Rectangle2D.Float object) {
		for(Platform plat: mPathmap) {
			if(plat.getDimensions().intersects(object))
				return plat;
		}
		return null;
	}
	
	public void registerTarget(Rectangle2D.Float pos) {
		mTarget = pos;
		
		if(mTarget != null)
			mTargetPlatform = findIntersectingPlatform(mTarget);
		else
			mTargetPlatform = null;
	}
	
	public Rectangle2D.Float getTarget() {
		return mTarget;
	}
	
	public Platform getTargetPlatform() {
		return mTargetPlatform;
	}
	
	public LinkedList<Platform> generatePath(Platform start) { /*
		if(mTarget == null || mTargetPlatform == null || mPathmap.isEmpty())
			return null;
		
		LinkedList<LinkedList<Platform>> pathQueue = new LinkedList<LinkedList<Platform>>(); // rofl linked list inside a linked list XD win!
		int targetPlatformID = mTargetPlatform.getID();
		
		LinkedList<Platform> startPath = new LinkedList<Platform>();
		startPath.add(start);
		
		pathQueue.add(startPath);
		
		LinkedList<Platform> path;
		Platform platform;
		while(!pathQueue.isEmpty()) {
			path = pathQueue.pollLast();
			platform = path.pollLast();
			
			// Iterate through all the linked platforms
			LinkedList<PlatformLink> linkList = platform.getPlatformLinks();
			Linkage:
			for(PlatformLink link: linkList) {
				int linkedID = link.getGoalPlatform().getID();
				
				// 2. Check if the platform has been reached by other paths
				// 2.a Check current path first
				for(int j = 0; j < path.size(); j++) {
					if(path.get(j).ID == linkedID) {
						continue Linkage;
					}
				}

				// 2.b Check paths in the queue
				for(int j = 0; j < pathQueue.size(); j++) {
					LinkedList<Platform> otherPath = pathQueue.get(j);
					for(int k = 0; k < otherPath.size(); k++) {
						if(otherPath.get(k).ID == linkedID) {
							continue Linkage;
						}
					}
				}
				
				Platform newPlatform = new Platform(platform.mDimensions, platform.ID);
				newPlatform.AddLink(link);
				LinkedList<Platform> newList = new LinkedList<Platform>(path);
				newList.add(newPlatform);
				
				// Check if it's the goal platform
				if(linkedID == targetPlatformID) {
					return copyPath(newList);
				}

				// Branch out to linked platform
				newList.addLast(link.mGoalPlatform);
				pathQueue.addLast(newList);
			}
		}
		
		// Path could not be found
		return null;
	} //*/ return null; }
	
	private LinkedList<Platform> copyPath(LinkedList<Platform> path) { /*
		LinkedList<Platform> nPath = new LinkedList<Platform>();
		
		ListIterator<Platform> pit = path.listIterator();
		Platform plat;
		PlatformLink link;
		LinkCell cell;
		while(pit.hasNext())
		{
			plat = pit.next();
			Platform nPlat = new Platform(plat.mDimensions, plat.ID);
			
			if(plat.mLinkedPlatforms.isEmpty())
				continue;
			
			link = plat.mLinkedPlatforms.getFirst();
			PlatformLink nLink = new PlatformLink(link.mGoalPlatform);
			
			if(link.mLinkCells.isEmpty())
				continue;
			
			cell = link.mLinkCells.getLast();
			while(cell != null) 
			{
				nLink.addLinkCell(cell.mDimensions);
				cell = cell.mParentCell;
			}
			
			nPlat.AddLink(nLink);
			nPath.addLast(nPlat);
		}
		
		return nPath;		
	} //*/ return null; }
	
	public void update() { /*
		if(mTarget == null || mTarget.intersects(mTargetPlatform.mDimensions))
			return;
		
		Platform newTargetPlat = findIntersectingPlatform(mTarget);
		if(newTargetPlat != null)
			mTargetPlatform = newTargetPlat;
	} //*/ }
}
