/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.lbourdages.shitty_a_star.data.Container2D;
import com.lbourdages.shitty_a_star.data.List2D;
import com.lbourdages.shitty_a_star.map.TileState;

public class ImageToRawMapConverter {

  private static final int BLACK = -16777216;
  private static final int RED = -65536;
  private static final int GREEN = -16711936;
  private static final int WHITE = -1;

  public static Container2D<TileState> readImage(File imageFile) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(imageFile);
    Container2D<TileState> rawMap = new List2D<>(bufferedImage.getWidth(), bufferedImage.getHeight(), TileState.EMPTY);
    int ySize = bufferedImage.getHeight();

    for (int x = 0; x < bufferedImage.getWidth(); x++) {
      for (int y = 0; y < bufferedImage.getHeight(); y++) {
        rawMap.set(x, y, getTileStateFromRGB(bufferedImage.getRGB(x, ySize - y)));
      }
    }

    return rawMap;
  }

  private static TileState getTileStateFromRGB(int RGB) {
    switch (RGB) {
      case BLACK:
        return TileState.OBSTACLE;
      case RED:
        return TileState.START;
      case GREEN:
        return TileState.GOAL;
      default:
        return TileState.EMPTY;
    }
  }
}
