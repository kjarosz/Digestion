package Graphics;

import java.awt.Image;
import java.util.Comparator;

public class ImageItem implements Comparator<ImageItem> {
	public Image image;
	public float x;
	public float y;
	public float z;
	public float width;
	public float height;
	
	ColorMode colorMode;

   @Override
	public int compare(ImageItem first, ImageItem second) {
      if(first.z < second.z)
         return -1;
      else if(first.z > second.z)
         return 1;
      
      return 0;
	}
}
