package com.lbourdages.shitty_a_star.map;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lbourdages.shitty_a_star.data.Node;
import com.lbourdages.shitty_a_star.data.Point;

/**
 * Created by mlachapelle on 2017-01-05.
 */
public interface Map2D {
   List<Point> aStar(Point startPoint, Point endPoint);
}
