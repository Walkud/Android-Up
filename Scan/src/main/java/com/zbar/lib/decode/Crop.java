package com.zbar.lib.decode;


/**
 * 修剪坐标及宽高
 */
public class Crop {
    private int x = 0;
    private int y = 0;
    private int cropWidth = 0;
    private int cropHeight = 0;

    public Crop(int x, int y, int cropWidth, int cropHeight) {
        this.x = x;
        this.y = y;
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
