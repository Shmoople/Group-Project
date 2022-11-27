import astar.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.geometry.Point2D;

public class What {
    /*
     * Generating all the connections is going to be a pain in the ass
     */
    // just do the manually for now

    public static void main(String[] args) {
        // great another run configuratoin

        // I'm doing this manually to see what the fuck happens
        Thing[] arrOfThings = {
                new Thing(0, 0, "a"),
                new Thing(10, 10, "b"),
                new Thing(40, 40, "c"),
                new Thing(0, 30, "d"),
                new Thing(10,30, "e"),
                new Thing(20,50,"f"),
                new Thing(30,30,"g")
            };

        HashSet<Thing> allThingNodes = new HashSet<Thing>();

        for(Thing t : arrOfThings) {
            allThingNodes.add(t);
        }

        // adjency list (omg what the fuck have I done this looks like a ゴミの山)
        HashMap<String, Set<String>> thingConnections = new HashMap<String, Set<String>>();

        thingConnections.put(arrOfThings[0].getId(), newHashSet("b"));
        thingConnections.put(arrOfThings[1].getId(), newHashSet("a","d","e"));
        thingConnections.put(arrOfThings[2].getId(), newHashSet("f"));
        thingConnections.put(arrOfThings[3].getId(), newHashSet("b"));
        thingConnections.put(arrOfThings[4].getId(), newHashSet("b","g","f"));
        thingConnections.put(arrOfThings[5].getId(), newHashSet("c","e"));
        thingConnections.put(arrOfThings[6].getId(), newHashSet("e"));

        // public Graph(Set<T> nodes, Map<String, Set<String>> connections)
        Graph<Thing> graphOfThings = new Graph<Thing>(allThingNodes, thingConnections);

        printAdjacencyList(graphOfThings);

        /* FIND THE ROUTE */
        RouteFinder<Thing> routeFinder = new RouteFinder<>(graphOfThings, new ThingScorer(), new ThingScorer());
        List<Thing> route = routeFinder.findRoute(graphOfThings.getNode("a"), graphOfThings.getNode("c"));

        for(Thing t : route) {
            System.out.println(t.getId());
        }
    }
    
    // Graph<Thing> graphOfThings = new Graph
    // List<Thing> route = routeFinder.findRoute

    /*implied generics is weird as fuck*/
    public static final <T> Set<T> newHashSet(T... objs) { // Pollution? Yeah I don't think I'll be doing weird casts
        Set<T> set = new HashSet<T>(); // what
        Collections.addAll(set, objs);
        return set;
    }

    public static final <T extends GraphNode> void printAdjacencyList(Graph<T> g) {
        System.out.println("Adjacency list printer 9999 --> ");
        for(T t : g.getNodes()) {
            System.out.print(t.getId()+": ");
            for(T f : g.getConnections(t)) {
                System.out.print(f.getId()+" ");
            }
            System.out.println();
        }
        System.out.println("--> End of adjacency list!\n");
    }
}

class ThingScorer implements Scorer<Thing> {
    @Override
    public double computeCost(Thing from, Thing to) {
        return from.getPosition().distance(to.getPosition());
    }
}

class Thing implements GraphNode {

    private Point2D position;
    private String id;

    public Thing(double x, double y, String id) {
        this.id = id;
        this.position = new Point2D(x,y);
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public String getId() {
        return id;
    }
}
