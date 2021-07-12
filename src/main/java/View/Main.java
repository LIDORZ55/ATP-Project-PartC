package View;

import Model.IModel;
import Model.MyModel;
import Server.Server;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;

import java.io.File;
import java.util.Random;

public class Main extends Application {

    public static int Port_ServerMazeGenerating = getRandomNumber(5000, 6000);
    public static int Port_ServerSearchProblemSolver = getRandomNumber(6001, 7000);
    public static Server mazeGeneratingServer;
    public static Server solveSearchProblemServer;
    public static MediaPlayer mediaPlayer;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Luigi's Maze Game");
        primaryStage.setScene(new Scene(root, 967, 506));
        primaryStage.show();
        Port_ServerMazeGenerating = getRandomNumber(5000, 6000);
        Port_ServerSearchProblemSolver = getRandomNumber(6001, 7000);
        mazeGeneratingServer = new Server(Port_ServerMazeGenerating, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(Port_ServerSearchProblemSolver, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController(); //we get the controller (view) from the loader
        view.setViewModel(viewModel);



        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                solveSearchProblemServer.stop();
                mazeGeneratingServer.stop();
                Platform.exit();
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }



    private static int getRandomNumber(int from, int to) {
        return from < to ? from + (new Random()).nextInt(Math.abs(to - from)) : from - (new Random()).nextInt(Math.abs(to - from));
    }
}
