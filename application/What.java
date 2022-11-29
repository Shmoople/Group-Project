/*
 * implementation file for stuff (this is a total pain in the butt!)
 */

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

        Graph<Thing> graphyGraphington = createGrid(8,3,10); // create a 5x5 grid with a spacing of 10 between each node! (the first node is at 0,0)
        
        RouteFinder<Thing> routeFinder = new RouteFinder<Thing>(graphyGraphington, new ThingScorer(), new ThingScorer());
        List<Thing> route = routeFinder.findRoute(graphyGraphington.getNode("0-2"),graphyGraphington.getNode("7-2"));
        printRoute(route);
        // printAdjacencyList(graphyGraphington);

        // // great another run configuratoin

        // // I'm doing this manually to see what the fuck happens
        // Thing[] arrOfThings = {
        //         new Thing(0, 0, "a"),
        //         new Thing(10, 10, "b"),
        //         new Thing(40, 40, "c"),
        //         new Thing(0, 30, "d"),
        //         new Thing(10,30, "e"),
        //         new Thing(20,50,"f"),
        //         new Thing(30,30,"g")
        //     };

        // HashSet<Thing> allThingNodes = new HashSet<Thing>();

        // for(Thing t : arrOfThings) {
        //     allThingNodes.add(t);
        // }

        // // adjency list (omg what the fuck have I done this looks like a ゴミの山)
        // HashMap<String, Set<String>> thingConnections = new HashMap<String, Set<String>>();

        // thingConnections.put(arrOfThings[0].getId(), newHashSet("b"));
        // thingConnections.put(arrOfThings[1].getId(), newHashSet("a","d","e"));
        // thingConnections.put(arrOfThings[2].getId(), newHashSet("f"));
        // thingConnections.put(arrOfThings[3].getId(), newHashSet("b"));
        // thingConnections.put(arrOfThings[4].getId(), newHashSet("b","g","f"));
        // thingConnections.put(arrOfThings[5].getId(), newHashSet("c","e"));
        // thingConnections.put(arrOfThings[6].getId(), newHashSet("e"));

        // // public Graph(Set<T> nodes, Map<String, Set<String>> connections)
        // Graph<Thing> graphOfThings = new Graph<Thing>(allThingNodes, thingConnections);

        // printAdjacencyList(graphOfThings);

        // /* FIND THE ROUTE */
        // RouteFinder<Thing> routeFinder = new RouteFinder<>(graphOfThings, new ThingScorer(), new ThingScorer());
        // List<Thing> route = routeFinder.findRoute(graphOfThings.getNode("a"), graphOfThings.getNode("c"));

        // for(Thing t : route) {
        //     System.out.println(t.getId());
        // }
    }
    
    // Graph<Thing> graphOfThings = new Graph
    // List<Thing> route = routeFinder.findRoute

    public static <T extends GraphNode> void printRoute(List<T> l) {
        System.out.print("start: ");
        for(T t : l) {
            System.out.print(t.getId()+"->");
        }
        System.out.println("end");
    }


    public static Graph<Thing> createGrid(int width, int height, int spacing) {

        /* refer to createStrongGrid for the pain it took to figure this out */
        /* I decided to use if statements because y e s */
        Thing[][] nodes = new Thing[width][height];
        
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                nodes[i][j] = new Thing(i*spacing,j*spacing,String.format("%d-%d",i,j));
            }
        }

        HashSet<Thing> nodeSet = new HashSet<Thing>();

        for(Thing[] i : nodes) {
            for(Thing t : i) {
                nodeSet.add(t);
            }
        }

        HashMap<String, Set<String>> nodeMap = new HashMap<String, Set<String>>();

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {

                Set<String> adjacentNodeIDs = new HashSet<String>();

                // who needs for loops anyways?
                if(j > 0) adjacentNodeIDs.add(nodes[i][j-1].getId());             
                if(i > 0) adjacentNodeIDs.add(nodes[i-1][j].getId());             
                if(i < nodes.length-1) adjacentNodeIDs.add(nodes[i+1][j].getId());             
                if(j < nodes[i].length-1) adjacentNodeIDs.add(nodes[i][j+1].getId());             

                nodeMap.put(nodes[i][j].getId(), adjacentNodeIDs); // this works!
            }
        }
    
        return new Graph<Thing>(nodeSet, nodeMap);
    }

    public static Graph<Thing> createDiagnolGrid(int width, int height) {
        return null;
    }
        // for(int i = 0; i < arr.length; i++) {
        //     for(int j = 0; j < arr[i].length; j++) {
        //         if(j > 0) System.out.println(arr[i][j-1]);
        //         if(i > 0) System.out.println(arr[i-1][j]);
        //         if(i < arr.length-1) System.out.println(arr[i+1][j]);
        //         if(j < arr.length-1) System.out.println(arr[i][j+1]);
        //     }
        // }
    public static Graph<Thing> createStrongGrid(int width, int height, int spacing) {

        // create nodes
        Thing[][] nodes = new Thing[width][height]; // this should be enough space for all the nodes in the graph
        /* ok so I had no idea that you can't create generic arrays, so this entire implementation is a bust,
         * but it works for the time being so whatever (I'll have to see if there is a way to use sets instead,
         * should also remove the need for create new sets to store nodes)
         */

        // initialize each node object (this shouldn't be that time consuming)
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                nodes[i][j] = new Thing(i*spacing,j*spacing,String.format("%d-%d",i,j)); // so the position of each node needs to be determined by a spacing... (let user specify it)
                // id system looks like this: "0-0" "x-y" REMEMBER THIS! ください！
            }
        }

        // create connections (edges)

        /*
         * In order to make some connections to nodes, you'll have to make sure that every node is accounted for (including
         * the nodes that are adjacent to an edge) this can be done by examining all nodes around each individual node
         * 
         * So how the fuck does that work, how can we tell if a node is adjacent in the first place (I'm using adjacent not
         * in the sense of graph theory, but rather if a tile is literaly next to another tile)
         * 
         * Well we can use the array that was setup before, all adjacent tiles can be found using simple local technique
         * 
         * a b c    (i-1,j-1) (i,j-1)  (i+1,j-1)
         * d x e    (i-1, j)     x     (i+1,j)
         * f g h    (i-1, j+1) (i,j+1) (i+1,j+1)
         * 
         * So finding all adjacents and then adding their id's to the adjacency list of each node would work out just fine...
         * right?
         * 
         * Well we need to append each individual node to a set, and then add that set to a map with the iterator's id
         * 
         * This is a pain in the but... whatever it's easier than manually adding every single node to the graph
         */

        // ok

        // we have Thing[width][height] to work with right now

        // to create a set of connections we need to initialize a connections

        // thingConnections.put(arrOfThings[0].getId(), newHashSet("b")); // this is how to initialize a connection

        // create a set for the nodes

        // create a Map<String, Set<String>> for the connections (adjacency list)

        /*
         * This whole node set is stupid, I shouldn't have made an array to begin with (I don't wanna spend more time on
         * that shit anyways though, I'll figure out something else later)
         */

        HashSet<Thing> nodeSet = new HashSet<Thing>();

        /* populate the nodeSet (this is just putting all the nodes into the set from the 2d array) */
        for(Thing[] i : nodes) {
            for(Thing t : i) {
                nodeSet.add(t);
            }
        } // this is dumb, there shouldn't be any reason to have a 2D array here, like wtf

        HashMap<String, Set<String>> nodeMap = new HashMap<String, Set<String>>(); // this line is fucked
        
        // for(int i = 0; i < nodes.length; i++) {
        //     for(int j = 0; j < nodes[i].length; j++) {

        //         // nodes[i][j] (the current node being inspected)

        //         // nodes[i-1][j+1] (the node bottom left to the node being inspected)

        //         // how do we handle edge cases?

                
        //     }
        // } 

        for (int i = 0; i < nodes.length; i++) { // iterate through every node
            for (int j = 0; j < nodes[i].length; j++) { // ^ 

                Set<String> adjacentNodeIDs = new HashSet<String>(); // nodes adjacent to the current being inspected

                for (int x = Math.max(0, i - 1); x <= Math.min(i + 1, nodes.length); x++) { // avoids edge cases (iterate surrounding nodes)
                    for (int y = Math.max(0, j - 1); y <= Math.min(j + 1, nodes[i].length); y++) { // avoids edge cases (^)
                        if (x >= 0 && y >= 0 && x < nodes.length && y < nodes[i].length) { // makes sure that both values are greater than zero
                            if(x!=i || y!=j){ // understands that x and y can't also be equal to the node being inspected (don't add yourself to the adjacency list!)
                                adjacentNodeIDs.add(nodes[x][y].getId());

                                // this iteration technique actually creates a strong array,
                                // I think if you connect all the even iteration offsets then it would make a more
                                // uniform square pattern rather than diagnols
                                // I'll do that later... for now this should be good
                            }
                        }
                    }
                }
                
                nodeMap.put(nodes[i][j].getId(), adjacentNodeIDs); // this works!
            }
        }
    


        return new Graph<Thing>(nodeSet, nodeMap);

    }
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

class Thing implements GraphNode { // グラフノード

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

// end of the file there is 無 to be seen here...