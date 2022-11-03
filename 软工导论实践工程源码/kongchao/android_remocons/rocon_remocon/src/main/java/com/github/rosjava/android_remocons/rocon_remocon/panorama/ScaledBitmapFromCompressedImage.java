package com.github.rosjava.android_remocons.rocon_remocon.panorama;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.ros.android.BitmapFromCompressedImage;

public class ScaledBitmapFromCompressedImage extends BitmapFromCompressedImage
{
  private int scaleFactor = 1;

  public ScaledBitmapFromCompressedImage(int scale)
  {
    scaleFactor = scale;
  }



}
