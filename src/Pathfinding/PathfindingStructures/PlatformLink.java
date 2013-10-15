package Pathfinding.PathfindingStructures;

import java.awt.Rectangle;
import java.util.LinkedList;

public class PlatformLink {
	private LinkedList<LinkCell> mLinkChain;
	private Platform mParentPlatform;
	private Platform mGoalPlatform;
	
	public PlatformLink() {
		mLinkChain = new LinkedList<LinkCell>();
		mParentPlatform = null;
		mGoalPlatform = null;
	}
	
	public PlatformLink(Platform parentPlatform, Platform goalPlatform) {
		mLinkChain = new LinkedList<LinkCell>();
		mParentPlatform = parentPlatform;
		mGoalPlatform = goalPlatform;
	}
	
	public PlatformLink(PlatformLink link) {
		mLinkChain = new LinkedList<LinkCell>(link.mLinkChain);
		mParentPlatform = link.mParentPlatform;
		mGoalPlatform = link.mGoalPlatform;
	}
	
	public Platform getParentPlatform() {
		return mParentPlatform;
	}
	
	public void setParentPlatform(Platform parent) {
		mParentPlatform = parent;
	}
	
	public Platform getGoalPlatform() {
		return mGoalPlatform;
	}
	
	public void setGoalPlatform(Platform goal) {
		mGoalPlatform = goal;
	}
	
	public void addLinkCell(Rectangle dimensions) {
		LinkCell cell = new LinkCell(dimensions);
		if(!mLinkChain.isEmpty())
			cell.mParentCell = mLinkChain.getLast();
		mLinkChain.addLast(cell);
	}
	
	public LinkedList<Rectangle> getLinkCells() {
		LinkedList<Rectangle> list = new LinkedList<Rectangle>();
		LinkCell cell = mLinkChain.getLast();
		while(cell != null){
			list.add(cell.mDimensions);
			cell = cell.mParentCell;
		}
		return list;
	}
	
	public int getCellCount() {
		return mLinkChain.size();
	}
	
	public void removeCell(int index) {
		if(index < 0 || index >= mLinkChain.size())
			return;
		
		if(mLinkChain.size() == 1) {
			mLinkChain.clear();
			return;
		}
		
		LinkCell cell = mLinkChain.remove(index);
		if(index < mLinkChain.size())
			mLinkChain.get(index).mParentCell = cell.mParentCell;
	}
	
	public Rectangle getLastLinkCell() {
		return mLinkChain.getLast().mDimensions;
	}
	
	public boolean hasCell(Rectangle dimensions) {
		return findCellIndex(dimensions) != 0;
	}
	
	public int findCellIndex(Rectangle dimensions) {
		for(int i = 0; i < mLinkChain.size(); i++) {
			Rectangle cellDim = mLinkChain.get(i).mDimensions;
			if(cellDim.x == dimensions.x && cellDim.y == dimensions.y && cellDim.width == dimensions.width && cellDim.height == dimensions.height) {
				return i;
			}
		}

		return -1;
	}
	
	public PlatformLink invert() {
		if(mLinkChain.isEmpty())
			return null;
		
		PlatformLink link = new PlatformLink(mGoalPlatform, mParentPlatform);
		LinkCell cell = mLinkChain.getLast();
		while(cell != null) {
			link.addLinkCell(cell.mDimensions);
			cell = cell.mParentCell;
		}
		
		return link;
	}
}
