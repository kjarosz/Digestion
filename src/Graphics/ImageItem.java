package Graphics;

import java.awt.Image;
import java.util.Comparator;

public class ImageItem implements Comparator<ImageItem> {
	public Image image;
	public int x;
	public int y;
	public int z;
	public int width;
	public int height;
	
	ColorMode colorMode;

   @Override
	public int compare(ImageItem first, ImageItem second) {
		return first.z - second.z;
	}
}
