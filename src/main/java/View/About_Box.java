package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class About_Box {
    public static void display(){

        Stage window=new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        //Step A:set an title+ hight and whight of the Box
        window.setTitle("About");
        window.setHeight(700);
        window.setWidth(1000);
        //Step B:set an image
        Image Contact_Us_Image=null;
        try {
            Contact_Us_Image=new Image(new FileInputStream("resources/images/Maze_Sample.jpg"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image");
        }
        ImageView imageView = new ImageView(Contact_Us_Image);
        imageView.setFitHeight(160);
        imageView.setPreserveRatio(true);

        //Step C:set an Label for the Paragraph
        Label label_information=new Label();
        label_information.setText("Luigi’s Maze game is a Thinking video game developed and released by BGU Students Omer\n" +
                "and Lidor as a final project in the Advanced Program Courses.\n" +
                "In our Course we were required to create a video game application about building and\n" +
                "solving a maze. \n" +
                "In our project we have created a complete graphical user interface which the user can get a\n" +
                "full experience of a solid maze video game.\n" +
                "We worked on this application by dividing it into 3 main parts:\n" +
                "At first ,we created our maze by various different algorithms but the main one is the DFS\n" +
                "algorithm and this algorithm is the base of our maze generation and this algorithm is why we\n" +
                "are able to make complex mazes that have a very challenging solution.\n" +
                "After creating the maze we need to solve it, so we were using a verity of different searching\n" +
                "algorithms to help us reach the ending position and solve the maze, we used DFS,BFS and a\n" +
                "new version of the BFS algorithms which is more cost efficient.\n" +
                "In the second Part, we worked on communication between the client and the servers, the\n" +
                "servers are able to generate different mazes and solve different mazes using a threadpool to\n" +
                "organize the operations.\n" +
                "In the last part we created the GUI using javafx in which we displayed the mazes and their\n" +
                "unique solutions.\n");
        label_information.setFont(new Font("Arial", 20));
        label_information.setTextFill(Color.BLUE);
        //label.setGraphic(imageView);


        VBox layout=new VBox(10);
        Region spacer = new Region();
        spacer.setMinHeight(20);
        spacer.setMinWidth(20);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        layout.getChildren().addAll(label_information,spacer,imageView);

        layout.setAlignment(Pos.TOP_CENTER);
        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
