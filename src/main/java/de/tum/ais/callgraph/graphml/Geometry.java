package de.tum.ais.callgraph.graphml;

public class Geometry {

    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getWidth() {
        return width;
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


    public Point center() {
        return new Point(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

}
