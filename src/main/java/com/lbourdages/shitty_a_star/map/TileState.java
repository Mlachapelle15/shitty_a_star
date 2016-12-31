/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.map;

public enum TileState {
  EMPTY(" "),
  OBSTACLE("X"),
  GOAL("G"),
  START("S");

  private String printableValue;

  TileState(String printableValue) {
    this.printableValue = printableValue;
  }

  @Override
  public String toString() {
    return printableValue;
  }
}
