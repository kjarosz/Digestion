package Util;

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
	
	public Size(Size copy) {
		width = copy.width;
		height = copy.height;
	}
}
