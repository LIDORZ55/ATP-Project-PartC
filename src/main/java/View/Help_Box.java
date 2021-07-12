package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class Help_Box {
    public static void display(){

        Stage window=new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        //Step A:set an title+ hight and whight of the Box
        window.setTitle("Help");
        window.setHeight(800);
        window.setWidth(1000);
        //Step B:set an image
        Image Contact_Us_Image=null;
        try {
            Contact_Us_Image=new Image(new FileInputStream("resources/images/Help.jpg"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image");
        }
        ImageView imageView = new ImageView(Contact_Us_Image);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        //Step C:set an Label for the Paragraph
        Label label_information=new Label();
        label_information.setText("Welcome to the Maze Walkthrough. This page contains information to help you find\n" +
                "every Power Coin in every course for The Luigi’s Maze Game.\n" +
                "How to start playing?\n" +
                "Click the Menu-> file ->New \n" +
                "Follow the next 4 steps to achieve the ultimate gaming experience-and to Challenge your\n" +
                "thinking by using our game:\n" +
                "Step 1: Generate your maze! Select the dimensions of the maze\n" +
                "Start small and slowly increase the difficulty level-dimension in the maze\n" +
                "Step 2: Move Luigi Towards the goal! Using the arrow keys or the mouse.\n" +
                "If it’s too difficult then try using the show solution button - it could help you!\n" +
                "\n" +
                " After you finished the maze you have 2 options u can either start a new one or save the\n" +
                "maze u just finished so u can play it again later, you don’t have to finish the maze to save it\n" +
                "so if u see a maze that is really interesting you can save it right away. \n" +
                "To save go to the menu and then go to file->save.\n" +
                "The maze file will be saved in ur computer’s temp folder and u can always load it from there.\n" +
                "How can I load my previously saved maze?\n" +
                "You can load the maze by going to the menu and then file->load and then go to ur local temp\n" +
                "folder and select the maze file (not the solution) and then you can start solving the maze\n" +
                "again.\n");
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
