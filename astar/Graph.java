package astar;

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

        // first time working with streams and its making my balls bleed
        return nodes.stream()
        .filter(node -> node.getId().equals(id)) // so many fucking lambda expressions
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No node found with that ID!"));
    }

    public Set<T> getNodes() {
        return nodes.stream().collect(Collectors.toSet()); // dude why did I do that just return the fucking set im so drunk i need sleep i think i have a cold
    }

    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
        .map(this::getNode)
        .collect(Collectors.toSet());
    }
}