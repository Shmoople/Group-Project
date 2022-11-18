import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T extends GraphNode> {
    private final Set<T> nodes;

    private final Map<String, Set<String>> connections;

    public Graph(Set<T> nodes, Map<String, Set<String>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public T getNode(String id) {
        return nodes.stream()
        .filter(node -> node.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No node found with that ID!"));
    }

    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
        .map(this::getNode)
        .collect(Collectors.toSet());
    }
}

class RouteNode<T extends GraphNode> implements Comparable<RouteNode> {

    /* Fields */
    private final T current;
    private T previous;
    private double routeScore;
    private double estimatedScore;

    /* Constructors */
    public RouteNode(T current) {
        /* if there isn't any previous node, just pass a perfect score */
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    RouteNode(T current, T previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this. routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public T getCurrent() { return current; }
    public T getPrevious() { return previous; }
    public double getRouteScore() { return routeScore; }
    public double getEstimatedScore() { return estimatedScore; }

    public void setRouteScore(double routeScore) { this.routeScore = routeScore; }
    public void setEstimatedScore(double estimatedScore) { this.estimatedScore = estimatedScore; }




    @Override // オーバーライド
    public int compareTo(RouteNode other) {
        if(this.estimatedScore > other.estimatedScore) {
            return 1;
        } else if(this.estimatedScore < other.estimatedScore) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        // I'll define this shit later
        return "無い!";
    }
}

interface GraphNode {
    String getId(); // this is a functional interface!
}

interface Scorer<T extends GraphNode> {
    double computeCost(T from, T to); // another functional interface!
}

class RouteFinder<T extends GraphNode> {

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
    }

    /**
     * Returns a path generated by the A* algorithm
     * @param from the starting node in the path
     * @param to the ending node in the graph
     * @return a list containing a sequence of nodes that composes the "shortest" path
     */
    public List<T> findRoute(T from, T to) {

        Map<T, RouteNode<T>> allNodes = new HashMap<>();
        Queue<RouteNode>
        return null; // again I'll do this later さよなら！
    }
}



















