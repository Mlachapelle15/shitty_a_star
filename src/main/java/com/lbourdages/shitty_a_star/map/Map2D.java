/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.map;

import com.lbourdages.shitty_a_star.data.Graph;

public class Map2D {
  private Graph<TileState> graph;

  public Map2D(Graph<TileState> graph) {
    this.graph = graph;
  }
}
