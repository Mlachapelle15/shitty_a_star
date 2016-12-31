/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.map;

import com.lbourdages.shitty_a_star.data.Container2D;

public class Map {
  private Container2D<TileState> map;

  public Map(Container2D<TileState> originalMap) {
    this.map = originalMap;
  }
}
