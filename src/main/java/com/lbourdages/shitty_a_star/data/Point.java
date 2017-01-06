/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.data;

public class Point {
  private static final int epsilon = 3;

  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int manhattanDistance(Point other) {
    return Math.abs(other.getX() - x) + Math.abs(other.getY() - y);
  }

  public boolean isXCoordinateBetweenPoints(Point point1, Point point2) {
    return (point1.getX() >= x && point2.getX() <= x) || (point1.getX() <= x && point2.getX() >= x);
  }

  public boolean isYCoordinateBetweenPoints(Point point1, Point point2) {
    return (point1.getY() >= y && point2.getY() <= y) || (point1.getY() <= y && point2.getY() >= y);
  }

  public boolean isBetweenPoints(Point point1, Point point2) {
    // blame him http://stackoverflow.com/a/328122

    int crossProduct = (y - point1.getY()) * (point2.getX() - point1.getX()) - (x - point1.getX()) * (point2.getY() - point1.getY());
    if (Math.abs(crossProduct) > epsilon) {
      return false;
    }

    int dotProduct = (x - point1.getX()) * (point2.getX() - point1.getX()) + (y - point1.getY()) * (point2.getY() - point1.getY());
    if (dotProduct < 0) {
      return false;
    }

    int squareOfDistanceBetweenPoint1AndPoint2 = (point2.getX() - point1.getX()) * (point2.getX() - point1.getX()) + (point2.getY() - point1.getY()) * (point2.getY() - point1.getY());
    if (dotProduct > squareOfDistanceBetweenPoint1AndPoint2) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Point (" + x + ',' + y + ")";
  }
}
