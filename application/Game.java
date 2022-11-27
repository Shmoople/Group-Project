 import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import astar.Graph;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application { // IT'S PUBLIC　パブリッく　クソオオ
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Intense abdominal pain");

        // Button btn = new Button();
        // btn.setText("Say 'Hello World'");
        // btn.setOnAction(new EventHandler<ActionEvent>() {
 
        //     @Override
        //     public void handle(ActionEvent event) {
        //         System.out.println("Hello World!");
        //     }
        // }); // え
        Group root = new Group();

        HashSet<Location> hs = new HashSet<Location>();
        Graph<Location> g = new Graph<Location>(hs, null);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

        
        // t1.run(); // should update all the stuff
        
    }
}

    