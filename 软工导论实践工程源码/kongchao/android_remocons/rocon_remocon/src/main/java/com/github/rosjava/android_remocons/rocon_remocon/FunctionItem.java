package com.github.rosjava.android_remocons.rocon_remocon;

/**
 * Created by turtlebot on 18-3-24.
 */

public class FunctionItem {
    private String name;
    private int imageId;

    public FunctionItem(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
