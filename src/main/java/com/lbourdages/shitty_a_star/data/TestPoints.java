package com.lbourdages.shitty_a_star.data;

public class TestPoints {
  private Point start;
  private Point end;

  public TestPoints(Point start, Point end) {
    this.start = start;
    this.end = end;
  }

  public Point getEnd() {
    return end;
  }

  public Point getStart() {
    return start;
  }
}
