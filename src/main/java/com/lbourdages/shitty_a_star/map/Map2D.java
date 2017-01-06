/**
 *Copyright (c) 2016 - 2016, Louis Bourdages
 */
package com.lbourdages.shitty_a_star.map;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Sets;
import com.lbourdages.shitty_a_star.data.Graph;
import com.lbourdages.shitty_a_star.data.Node;
import com.lbourdages.shitty_a_star.data.Point;

public class Map2D {
  private int LARGE_BUT_NOT_MAX_INT = 100000; // This is shitty BUT I'm just messing around. I don't want overflows.
  private Graph<TileState> graph;

  public Map2D(Graph<TileState> graph) {
    this.graph = graph;
  }


  public List<Point> aStar(Point startPoint, Point endPoint) {
    int numberOfEvaluatedNodes = 0;
    Node<TileState> startNode = graph.getNode(startPoint);
    Node<TileState> endNode = graph.getNode(endPoint);


    Set<Node<TileState>> evaluatedNodes = Sets.newHashSet();
//    Set<Node<TileState>> nodesToBeEvaluated = Sets.newHashSet(startNode);
    MinMaxPriorityQueue<Pair<Integer, Node<TileState>>> nodesToBeEvaluated = MinMaxPriorityQueue.<Pair<Integer, Node<TileState>>>orderedBy(Comparator.comparing(Pair::getValue0)).create();
    nodesToBeEvaluated.add(new Pair<>(0, startNode));
    Map<Node<TileState>, Node<TileState>> cameFrom = Maps.newHashMap();

    Map<Node<TileState>, Integer> costFromStartToNode = Maps.newHashMap();
    costFromStartToNode.put(startNode, 0);

    Map<Node<TileState>, Integer> costFromStartToGoalViaNode = Maps.newHashMap();
    costFromStartToGoalViaNode.put(startNode, heuristicCostEstimate(startNode, startPoint, startPoint, endPoint));

    while (!nodesToBeEvaluated.isEmpty()) {
      numberOfEvaluatedNodes++;
      Node<TileState> currentNode = nodesToBeEvaluated.removeFirst().getValue1();
      if (currentNode.equals(endNode)) {
        System.out.println("Evaluated " + numberOfEvaluatedNodes + "nodes");
        return reconstructPath(cameFrom, currentNode);
      }

      evaluatedNodes.add(currentNode);
      for (Node<TileState> neighbour : currentNode.getNeighbours()) {
        if (evaluatedNodes.contains(neighbour)) {
          continue; // We must not evaluate this twice
        }
        int neighbourScore = costFromStartToNode.getOrDefault(currentNode, LARGE_BUT_NOT_MAX_INT) + neighbour.getCoordinate().manhattanDistance(currentNode.getCoordinate());
        int heuristicCost = heuristicCostEstimate(neighbour, currentNode.getCoordinate(), startPoint, endPoint);
        if (nodesToBeEvaluated.stream().map(Pair::getValue1).noneMatch(node -> node.equals(neighbour))) {
          nodesToBeEvaluated.add(new Pair<>(neighbourScore + heuristicCost, neighbour));
        } else if (neighbourScore > costFromStartToNode.getOrDefault(neighbour, LARGE_BUT_NOT_MAX_INT)) {
          continue; // This would be a worse path
        }
        cameFrom.put(neighbour, currentNode);
        costFromStartToNode.put(neighbour, neighbourScore);
        costFromStartToGoalViaNode.put(neighbour, neighbourScore + heuristicCost);
      }
    }
    System.out.println("Evaluated " + numberOfEvaluatedNodes + "nodes");
    return Lists.newArrayList();
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

  private int heuristicCostEstimate(Node<TileState> node, Point currentNode, Point origin, Point goal) {
    if (node.getValue().equals(TileState.OBSTACLE)) {
      return LARGE_BUT_NOT_MAX_INT;
    }
    Point nodePoint = node.getCoordinate();

    int distance = goal.manhattanDistance(nodePoint) + 5;

    if (nodePoint.isXCoordinateBetweenPoints(origin, goal)) {
      distance -= 1;
    }
    if (nodePoint.isYCoordinateBetweenPoints(origin, goal)) {
      distance -= 1;
    }

    if (nodePoint.isBetweenPoints(origin, goal)) {
      distance -= 2;
    }

//    for (Node<TileState> neighbour : node.getNeighbours()) {
//      if (neighbour.getValue().equals(TileState.OBSTACLE)) {
//        distance += 2;
//      }
//    }

    return distance;
  }
}
