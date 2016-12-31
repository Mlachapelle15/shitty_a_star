/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.data;

public interface Container2D<T> {

  T get(int x, int y);

  void set(int x, int y, T value);
}
