/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.data;

public class Point {
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

  @Override
  public String toString() {
    return "Point (" + x + ',' + y + ")";
  }
}
