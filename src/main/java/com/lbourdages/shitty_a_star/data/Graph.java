/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.data;

import java.lang.reflect.Array;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public class Graph<T> {

  private Node<T>[][] nodes;
  private int xSize;
  private int ySize;

  @SuppressWarnings("unchecked")
  public Graph(int xSize, int ySize, T defaultValue) {
    this.xSize = xSize;
    this.ySize = ySize;
    nodes = (Node<T>[][]) Array.newInstance(Node.class, xSize, ySize);

    for (int x = 0; x < xSize; x++) {
      for (int y = 0; y < ySize; y++) {
        Node<T> node = new Node<>(defaultValue, Sets.newHashSet(), new Point(x, y));
        setNeighboursOfNewlyCreatedNode(node);
        addNodeToItsNeighboursNeighbours(node);
        nodes[x][y] = node;
      }
    }
  }

  public Node<T> getNode(Point point) {
    return nodes[point.getX()][point.getY()];
  }

  // I decided to not use diagonal movement for a start. Modify this if necessary.
  private void setNeighboursOfNewlyCreatedNode(Node<T> node) {
    int x = node.getCoordinate().getX();
    int y = node.getCoordinate().getY();
    Set<Node<T>> neighbours = Sets.newHashSet();
    //Let's start by the corner cases
    if (x != 0) {
      node.addNeighbour(nodes[x - 1][y]);
    }
    if (y != 0) {
      neighbours.add(nodes[x][y - 1]);
    }
  }

  private void addNodeToItsNeighboursNeighbours(Node<T> node) {
    node.getNeighbours().forEach(neighbour -> neighbour.addNeighbour(node));
  }

  public int getxSize() {
    return xSize;
  }

  public int getySize() {
    return ySize;
  }

  @Override
  public String toString() {
    String output = Strings.repeat("-", xSize + 2) + "\n";
    for (int y = 0; y < ySize; y++) {
      output = output + "|";
      for (int x = 0; x < xSize; x++) {
        output = output + nodes[x][y].toString();
      }
      output = output + "|\n";
    }
    output = output + Strings.repeat("-", xSize + 2) + "\n";
    return "Graph{\n" +
        output +
        "}";
  }
}
