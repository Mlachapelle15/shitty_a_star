package com.lbourdages.shitty_a_star.map;

import java.util.List;

import com.lbourdages.shitty_a_star.data.Point;

/**
 * Created by mlachapelle on 2017-01-05.
 */
public interface Map2D {
  List<Point> aStar(Point startPoint, Point endPoint);
}
