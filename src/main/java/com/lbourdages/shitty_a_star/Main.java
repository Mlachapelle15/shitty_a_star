/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.lbourdages.shitty_a_star.data.Graph;
import com.lbourdages.shitty_a_star.data.Point;
import com.lbourdages.shitty_a_star.map.Map2D;
import com.lbourdages.shitty_a_star.map.TileState;

public class Main {

  public static void main(String[] args) throws IOException {
//    File image = new File("/Users/lbourdages/Desktop/map_base.png");
    File image = new File("/Users/lbourdages/Desktop/map1.png");
    Graph<TileState> graphFromImage = ImageToRawGraphConverter.readImage(image);
    System.out.println(graphFromImage);

    Map2D map = new Map2D(graphFromImage);

    DateTime startTime = DateTime.now();
    List<Point> path = map.aStar(new Point(0, 0), new Point(70, 99));
    System.out.println(path);
    DateTime endTime = DateTime.now();
    System.out.println("A* took " + new Duration(startTime, endTime).getMillis() + " miliseconds.");

    path.forEach(point -> graphFromImage.getNode(point).setValue(TileState.PATH));
    startTime = DateTime.now();
    System.out.println(graphFromImage);
    endTime = DateTime.now();
    System.out.println("Printing of map took " + new Duration(startTime, endTime).getMillis() + " miliseconds.");
  }
}
