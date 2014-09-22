package Util;

import org.jbox2d.common.Vec2;

public class Size {
	public int width;
	public int height;
	
	public Size() {
		width = 0;
		height = 0;
	}
	
	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Size(Vec2 original) {
		width = (int)original.x;
		height = (int)original.y;
	}
	
	public Size(Size copy) {
		width = copy.width;
		height = copy.height;
	}
}
