package com.example.propertymanagment;

public class XYValues {
    //value class for the values in the finance class

    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public XYValues(double x, double y) {

        this.x = x;
        this.y = y;
    }
}
