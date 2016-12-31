/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star;

import java.io.File;
import java.io.IOException;

import com.lbourdages.shitty_a_star.data.Container2D;
import com.lbourdages.shitty_a_star.map.ImageToRawMapConverter;
import com.lbourdages.shitty_a_star.map.TileState;

public class Main {

  public static void main(String[] args) throws IOException {
    File image = new File("/Users/lbourdages/Desktop/map_base.png");
    Container2D<TileState> list = ImageToRawMapConverter.readImage(image);
    System.out.println(list);
  }
}
