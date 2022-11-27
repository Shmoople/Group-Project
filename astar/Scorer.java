package astar;

public interface Scorer<T extends GraphNode> {
    double computeCost(T from, T to); // another functional interface!
}

