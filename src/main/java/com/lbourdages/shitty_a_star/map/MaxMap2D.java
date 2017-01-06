/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.map;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lbourdages.shitty_a_star.data.Graph;
import com.lbourdages.shitty_a_star.data.Node;
import com.lbourdages.shitty_a_star.data.Point;

public class MaxMap2D implements Map2D {
  private int LARGE_BUT_NOT_MAX_INT = 100000; // This is shitty BUT I'm just messing around. I don't want overflows.
  private Graph<TileState> graph;

  public MaxMap2D(Graph<TileState> graph) {
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

  @Override
  public List<Point> aStar(Point startPoint, Point endPoint) {
    Node<TileState> startNode = graph.getNode(startPoint);
    Node<TileState> endNode = graph.getNode(endPoint);


    Set<Node<TileState>> evaluatedNodes = Sets.newHashSet();
    Set<Node<TileState>> nodesToBeEvaluatedLaterMaybe = Sets.newHashSet(startNode);
    Set<Node<TileState>> nodesToBeEvaluated = Sets.newHashSet(startNode);
    Map<Node<TileState>, Node<TileState>> cameFrom = Maps.newHashMap();

    Map<Node<TileState>, Integer> costFromStartToNode = Maps.newHashMap();
    costFromStartToNode.put(startNode, 0);

    Map<Node<TileState>, Integer> costFromStartToGoalViaNode = Maps.newHashMap();
    costFromStartToGoalViaNode.put(startNode, heuristicCostEstimate(startNode, endPoint));

    int border = 2;
    int targetMinX = Math.min(startPoint.getX(), endPoint.getX());
    int targetMaxX = Math.max(startPoint.getX(), endPoint.getX());
    int targetMinY = Math.min(startPoint.getY(), endPoint.getY());
    int targetMaxY = Math.max(startPoint.getY(), endPoint.getY());

    while (!nodesToBeEvaluatedLaterMaybe.isEmpty()) {
      Node<TileState> currentNode = null;
      Optional<Map.Entry<Node<TileState>, Integer>> temp = costFromStartToGoalViaNode
          .entrySet()
          .stream()
          .filter(entry -> nodesToBeEvaluated.contains(entry.getKey()))
          .sorted(Comparator.comparing(entry -> entry.getValue()))
          .findFirst();

      if (!temp.isPresent()) {
        // todo: seems to have a problem sometime
        System.out.println("Da Fuck?");
      } else {
        currentNode = temp.get()
            .getKey();
      }
      if (currentNode.equals(endNode)) {
        return reconstructPath(cameFrom, currentNode);
      }

      nodesToBeEvaluatedLaterMaybe.remove(currentNode);
      nodesToBeEvaluated.remove(currentNode);
      evaluatedNodes.add(currentNode);
      for (Node<TileState> neighbour : currentNode.getNeighbours()) {
        if (evaluatedNodes.contains(neighbour)) {
          continue; // We must not evaluate this twice
        }
        int neighbourScore = costFromStartToNode.getOrDefault(currentNode, LARGE_BUT_NOT_MAX_INT) + neighbour.getCoordinate().manhattanDistance(currentNode.getCoordinate());
        int cost = heuristicCostEstimate(neighbour, endPoint);
        if (cost < LARGE_BUT_NOT_MAX_INT && !nodesToBeEvaluatedLaterMaybe.contains(neighbour)) {
          nodesToBeEvaluatedLaterMaybe.add(neighbour);
          if (!nodesToBeEvaluated.contains(neighbour) && neighbourIsInTheBorder(neighbour, targetMinX, targetMaxX, targetMinY, targetMaxY, border)) {
            nodesToBeEvaluated.add(neighbour);
          }
        } else if (neighbourScore > costFromStartToNode.getOrDefault(neighbour, LARGE_BUT_NOT_MAX_INT)) {
          continue; // This would be a worse path
        }

        if (cost < LARGE_BUT_NOT_MAX_INT) {
          cameFrom.put(neighbour, currentNode);
          costFromStartToNode.put(neighbour, neighbourScore);
          costFromStartToGoalViaNode.put(neighbour, neighbourScore + cost);
        }
        if (nodesToBeEvaluated.isEmpty()) {
          nodesToBeEvaluated.addAll(nodesToBeEvaluatedLaterMaybe);
          border++;
        }
      }
    }

    return Lists.newArrayList();
  }

  private boolean neighbourIsInTheBorder(Node<TileState> neighbour, int minX, int maxX, int minY, int maxY, int border) {
    boolean isIn = neighbour.getCoordinate().getX() >= (minX - border)
        && neighbour.getCoordinate().getX() <= (maxX + border)
        && neighbour.getCoordinate().getY() >= (minY - border)
        && neighbour.getCoordinate().getY() <= (maxY + border);
    //System.out.println("Border(" + minX +","+ maxX +","+ minY +","+ maxY +")   -> (" + neighbour.getCoordinate().getX() + "," + neighbour.getCoordinate().getY() + ")   =   " + isIn);
    return isIn;
  }
}
