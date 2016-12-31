/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star;

import java.io.File;
import java.io.IOException;

import com.lbourdages.shitty_a_star.data.Graph;
import com.lbourdages.shitty_a_star.map.Map2D;
import com.lbourdages.shitty_a_star.map.TileState;

public class Main {

  public static void main(String[] args) throws IOException {
    File image = new File("/Users/lbourdages/Desktop/map_base.png");
    Graph<TileState> graphFromImage = ImageToRawGraphConverter.readImage(image);
    System.out.println(graphFromImage);

    Map2D map = new Map2D(graphFromImage);
  }
}
