/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.lbourdages.shitty_a_star.data.Graph;
import com.lbourdages.shitty_a_star.data.Point;
import com.lbourdages.shitty_a_star.map.Map2D;
import com.lbourdages.shitty_a_star.map.TileState;

public class Main {

  public static void main(String[] args) throws IOException {
//    File image = new File("/Users/lbourdages/Desktop/map_base.png");
    File image = new File("/Users/lbourdages/Desktop/map_hard.png");
    Graph<TileState> graphFromImage = ImageToRawGraphConverter.readImage(image);
    System.out.println(graphFromImage);

    Map2D map = new Map2D(graphFromImage);

    List<Point> path = map.aStar(new Point(0, 0), new Point(19, 19));
    System.out.println(path);

    path.forEach(point -> graphFromImage.getNode(point).setValue(TileState.PATH));
    System.out.println(graphFromImage);
  }
}
