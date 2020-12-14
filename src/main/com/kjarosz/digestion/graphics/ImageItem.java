package com.kjarosz.digestion.graphics;

import java.awt.Image;
import java.util.Comparator;

public class ImageItem implements Comparator<ImageItem> {
	public Image image;
	public double x;
	public double y;
	public double z;
	public double width;
	public double height;
	
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
