/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.map;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lbourdages.shitty_a_star.data.Graph;
import com.lbourdages.shitty_a_star.data.Node;
import com.lbourdages.shitty_a_star.data.Point;

public class LouisMap2D implements Map2D {
  private int LARGE_BUT_NOT_MAX_INT = 100000; // This is shitty BUT I'm just messing around. I don't want overflows.
  private Graph<TileState> graph;

  public LouisMap2D(Graph<TileState> graph) {
    this.graph = graph;
  }


  List<Point> reconstructPath(Map<Node<TileState>, Node<TileState>> cameFrom, Node<TileState> node) {
    Node<TileState> current = node;
    List<Point> backwardsPath = Lists.newArrayList(current.getCoordinate());

    while (cameFrom.containsKey(current)) {
      current = cameFrom.get(current);
      backwardsPath.add(current.getCoordinate());
    }
    return Lists.reverse(backwardsPath);
  }

  private int heuristicCostEstimate(Node<TileState> node, Point goal) {
    if (node.getValue().equals(TileState.OBSTACLE)) {
      return LARGE_BUT_NOT_MAX_INT;
    }

    int distance = goal.manhattanDistance(node.getCoordinate());

    for (Node<TileState> neighbour : node.getNeighbours()) {
      if (neighbour.getValue().equals(TileState.OBSTACLE)) {
        distance = distance + 2;
      }
    }

    return distance;
  }
}
