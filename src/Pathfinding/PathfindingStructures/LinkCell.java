package Pathfinding.PathfindingStructures;

import java.awt.Rectangle;

public class LinkCell {
	public Rectangle mDimensions;
	public LinkCell mParentCell;

	public LinkCell(Rectangle dim) {
		mDimensions = dim;
		mParentCell = null;
	}
}