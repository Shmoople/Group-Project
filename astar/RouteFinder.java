package astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class RouteFinder<T extends GraphNode> {

    private final Graph<T> graph;
    private final Scorer<T> nextNodeScorer;
    private final Scorer<T> targetScorer;

    /**
     * The primary RouteFinder constructor, use this when finding a route through a graph of nodes
     * 
     * @param graph a graph containing nodes (Contains a set of nodes and a map of edges)
     * @param nextNodeScorer an implementation of a scorer interface (used to determine 'f' value)
     * @param targetScorer an implementation of a scorer interface (used to determine 'h' value)
     */
    public RouteFinder(Graph<T> graph, Scorer<T> nextNodeScorer, Scorer<T> targetScorer) {
        this.graph = graph;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    } // hello 

    /**
     * Returns a path generated by the A* algorithm
     * @param from the starting node in the path
     * @param to the ending node in the graph
     * @return a list containing a sequence of nodes that composes the "shortest" path
     */
    public List<T> findRoute(T from, T to) {

        Map<T, RouteNode<T>> allNodes = new HashMap<>();
        
        // define the open set
        Queue<RouteNode<T>> openSet = new PriorityQueue<>();

        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from,to));
        allNodes.put(from,start); // remember that allNodes is a map that contains a mapping of GraphNode subclass instances and RouteNodes instances
        openSet.add(start); // add the starting node to the open set (this is apart of the algorithm if I can remember the notes)
        
        while(!openSet.isEmpty()) {
            RouteNode<T> next = openSet.poll(); // get the next node that is going to be examined (popped from the head of the queue)

            if(next.getCurrent().equals(to)) { // if we have reached the destination
                List<T> route = new ArrayList<>(); // this is the arraylist containing the route from point A to point B
                RouteNode<T> current = next; // visualising this action as iterating over nodes
                
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while(current != null);

                return route; // we're done! (first option)
            }

            graph.getConnections(next.getCurrent()).forEach(connection -> {
                // calculate the f value for the node (since this is the most general form of the algorithm, we will use Euclidean Distance)
                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection); // the score is determined by the distance algorithm
                // I'll make some more implementations for finding a better h value in the NodeScorer class
                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                allNodes.put(connection, nextNode);

                if(nextNode.getRouteScore() > newScore) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection,to));
                    openSet.add(nextNode);
                }

            });
        }

        // Right now this just throws an exception if no route if found (this code won't throw an exception if there is always a route [in other words this is garbage])
        throw new IllegalStateException("No route found"); // I'll make a handle for this when my brain stops hurting
        
        // return null; // well... loks like there wasn't a route! (I'll make a handle for this...)
    }
}

// 無い
