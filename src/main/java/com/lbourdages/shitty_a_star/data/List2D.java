/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class List2D<T> implements Container2D<T> {
  // This list is a collection of columns. Each element of the list is a list,
  // which itself is a column containing all elements for said column.
  private List<List<T>> listOfColumns;
  private int xSize;
  private int ySize;

  public List2D(int xSize, int ySize, T initialValue) {
    this.xSize = xSize;
    this.ySize = ySize;
    listOfColumns = Lists.newArrayListWithCapacity(xSize);
    for (int i = 0; i < xSize; i++) {
      listOfColumns.add(i, new ArrayList<>(Collections.nCopies(ySize, initialValue)));
    }
  }

  public T get(int x, int y) {
    checkBoundaries(x, y);
    return listOfColumns.get(x).get(y);
  }

  public void set(int x, int y, T value) {
    checkBoundaries(x, y);
    listOfColumns.get(x).set(y, value);
  }

  @Override
  public String toString() {
    String output = Strings.repeat("-", xSize + 2) + "\n";
    for (int y = ySize - 1; y >= 0; y--) {
      output = output + "|";
      for (int x = 0; x < xSize; x++) {
        output = output + listOfColumns.get(x).get(y).toString();
      }
      output = output + "|\n";
    }
    output = output + Strings.repeat("-", xSize + 2) + "\n";
    return "List2D{\n" +
        output +
        "}";
  }

  public void checkBoundaries(int x, int y) {
    if (x < 0 || y < 0 || x >= xSize || y >= ySize) {
      throw new IndexOutOfBoundsException("(" + x + "," + y + ") is out of bounds for list.");
    }
  }
}
