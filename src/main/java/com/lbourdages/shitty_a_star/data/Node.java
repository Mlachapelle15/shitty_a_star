/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.data;

import java.util.Set;

public class Node<T> {
  private T value;
  private Set<Node<T>> neighbours;
  private Point coordinate;

  public Node(T value, Set<Node<T>> neighbours, Point coordinate) {
    this.value = value;
    this.neighbours = neighbours;
    this.coordinate = coordinate;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public Point getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Point coordinate) {
    this.coordinate = coordinate;
  }


  public void addNeighbour(Node<T> neighbour) {
    neighbours.add(neighbour);
  }

  public Set<Node<T>> getNeighbours() {
    return neighbours;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
