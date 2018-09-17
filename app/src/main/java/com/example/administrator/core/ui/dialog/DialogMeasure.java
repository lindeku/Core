package com.example.administrator.core.ui.dialog;

public class DialogMeasure {
    public static final int DP=0;
    public static final int DX=1;
    private int type=DP;
    private double  width;
    private double  height;

    public double getWidth() {
        return width;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
