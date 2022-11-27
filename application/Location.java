import astar.*; // grab everything from the astar suite of tools
import javafx.geometry.Point2D; // who would want to make their own point class?
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/*
 * Right now we just want to make a class that represents locations on the graph,
 * then display edges to the locations (then we can worry about traversal)
 */


public class Location implements GraphNode, Scorer<Location>, Displayable { // directly bound to group?

    private Circle r2;
    private String id;
    private Point2D position;

    public Location(Point2D position, String id) {
        this.id = id;
        this.position = position;
        this.r2 = new Circle(position.getX(), position.getY(), 10, Color.RED); // x y r color

    }

    public Point2D getPosition() {return this.position; }

    @Override
    public Node display() { // gonna need javafx for this stuff... WE'LL BE OK YEAAAAAAAAAAAAAAAAAAAAAAAA 大丈夫!
        r2.setCenterX(position.getX());
        r2.setCenterY(position.getY());
        return r2;
    }

    @Override
    public double computeCost(Location from, Location to) {
        // just return the euclidean distance (distance formula)
        return from.position.distance(to.getPosition());
    }

    @Override
    public String getId() {
        return id;
    }
}
