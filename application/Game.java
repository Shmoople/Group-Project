 import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Painter;

import astar.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Game extends Application { // IT'S PUBLIC　パブリッく　クソオオ
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Intense abdominal pain");

        Group root = new Group();

        HashSet<Location> hs = new HashSet<Location>();
        Graph<Location> g = new Graph<Location>(hs, null);

        Graph<Thing> grid = What.createGrid(200,200,10);

        RouteFinder<Thing> routeFinder = new RouteFinder<Thing>(grid, new ThingScorer(), new ThingScorer());

        List<Thing> route = routeFinder.findRoute(grid.getNode("5-30"),grid.getNode("199-0"));

        Game.displayGraph(root, grid, 1, 1);
        Game.displayRoute(root, route, 5);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static <T extends Thing> void displayGraph(Group root, Graph<T> graph, int size, int offset) {
        for(Thing n : graph.getNodes()) {
            // display the node on the scene
            Circle displayObject = new Circle(n.getPosition().getX()+offset,n.getPosition().getY()+offset,size); // displayObject is the Circle thing
            displayObject.setFill(Color.RED);
            root.getChildren().add(displayObject);
        }
    }

    public static <T extends Thing> void displayRoute(Group root, List<T> route, int thickness) {
        for(int i = 0; i < route.size()-1; i++) {
            
            Line displayObject = new Line(route.get(i).getPosition().getX(), route.get(i).getPosition().getY(), route.get(i+1).getPosition().getX(), route.get(i+1).getPosition().getY());
            displayObject.setStrokeWidth(thickness);
            root.getChildren().add(displayObject);
        }
    }
}

    