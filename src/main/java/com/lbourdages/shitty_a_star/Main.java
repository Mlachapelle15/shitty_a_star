/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.lbourdages.shitty_a_star.data.Graph;
import com.lbourdages.shitty_a_star.data.Point;
import com.lbourdages.shitty_a_star.data.TestPoints;
import com.lbourdages.shitty_a_star.map.LouisMap2D;
import com.lbourdages.shitty_a_star.map.Map2D;
import com.lbourdages.shitty_a_star.map.MaxMap2D;
import com.lbourdages.shitty_a_star.map.TileState;

public class Main {

  public static void main(String[] args) throws IOException {
    benchmark();
  }

  private static void mainWithDisplay() throws IOException {

//    File image = new File("/Users/lbourdages/Desktop/map_base.png");
    File image = new File("./src/main/resources/map2.png");
    Graph<TileState> graphFromImage = ImageToRawGraphConverter.readImage(image);
    System.out.println(graphFromImage);

    //Point startPoint = new Point(4, 14);
    //Point endPoint = new Point(89, 76);
    Point startPoint = new Point(70, 0);
    Point endPoint = new Point(11, 70);

    Date startTime = new Date();
    Map2D map = new LouisMap2D(graphFromImage);
    List<Point> path = map.aStar(startPoint, endPoint);
    Date endTime = new Date();
    System.out.println(path);

    path.forEach(point -> graphFromImage.getNode(point).setValue(TileState.LOUIS));
    System.out.println(graphFromImage);


    Date startTimeMax = new Date();
    Map2D mapMax = new MaxMap2D(graphFromImage);
    List<Point> pathMax = mapMax.aStar(startPoint, endPoint);
    Date endTimeMax = new Date();
    System.out.println(pathMax);
    pathMax.forEach(point -> graphFromImage.getNode(point).setValue(TileState.MAX));
    System.out.println(graphFromImage);

    System.out.println("Time elapsed(Louis): " + (Duration.ofMillis(endTime.toInstant().toEpochMilli() - startTime.toInstant().toEpochMilli())) + " and cost: " + path.size());
    System.out.println("Time elapsed(Max): " + (Duration.ofMillis(endTimeMax.toInstant().toEpochMilli() - startTimeMax.toInstant().toEpochMilli())) + " and cost: " + pathMax.size());
  }

  private static void benchmark() throws IOException {
    File image = new File("./src/main/resources/map1.png");
    Graph<TileState> graphFromImage = ImageToRawGraphConverter.readImage(image);
    System.out.println(graphFromImage);

    List<TestPoints> useCases = Lists.newArrayList(new TestPoints(new Point(70, 0), new Point(11, 70)),
        new TestPoints(new Point(0, 0), new Point(99, 99)),
        new TestPoints(new Point(99, 99), new Point(0, 70)),
        new TestPoints(new Point(70, 0), new Point(11, 70)),
        new TestPoints(new Point(4, 14), new Point(89, 76)),
        new TestPoints(new Point(70, 0), new Point(50, 99)));

    for (int i = 0; i < 100; i++) {
      Random random = new Random();
      Point startPoint;
      do {
        int startX = random.nextInt(100);
        int startY = random.nextInt(100);
        startPoint = new Point(startX, startY);
      } while (!(graphFromImage.getNode(startPoint).getValue() == TileState.EMPTY));

      Point endPoint;
      do {
        int endX = random.nextInt(100);
        int endY = random.nextInt(100);
        endPoint = new Point(endX, endY);
      } while (!(graphFromImage.getNode(endPoint).getValue() == TileState.EMPTY));

      useCases.add(new TestPoints(startPoint, endPoint));
    }

    int avgLouisDuration = 0;
    int avgMaxDuration = 0;
    int avgLouisCost = 0;
    int avgMaxCost = 0;
    for (TestPoints testPoints : useCases) {
      try {
        Date startTime = new Date();
        Map2D map = new LouisMap2D(graphFromImage);
        List<Point> path = map.aStar(testPoints.getStart(), testPoints.getEnd());
        Date endTime = new Date();

        Date startTimeMax = new Date();
        Map2D mapMax = new MaxMap2D(graphFromImage);
        List<Point> pathMax = mapMax.aStar(testPoints.getStart(), testPoints.getEnd());
        Date endTimeMax = new Date();

        Duration louisDuration = Duration.ofMillis(endTime.toInstant().toEpochMilli() - startTime.toInstant().toEpochMilli());
        Duration maxDuration = Duration.ofMillis(endTimeMax.toInstant().toEpochMilli() - startTimeMax.toInstant().toEpochMilli());

        System.out.println("Time elapsed(Louis): " + louisDuration + " and cost: " + path.size());
        System.out.println("Time elapsed(Max): " + maxDuration + " and cost: " + pathMax.size());
        System.out.println();

        avgLouisDuration += louisDuration.toMillis();
        avgMaxDuration += maxDuration.toMillis();
        avgLouisCost += path.size();
        avgMaxCost += pathMax.size();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    avgLouisDuration /= useCases.size();
    avgMaxDuration /= useCases.size();
    avgLouisCost /= useCases.size();
    avgMaxCost /= useCases.size();

    System.out.println("Average elapsed time (Louis): " + avgLouisDuration + "ms and cost: " + avgLouisCost + " steps");
    System.out.println("Average elapsed time (Max): " + avgMaxDuration + "ms and cost: " + avgMaxCost + " steps");
  }
}
