package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.io.File;

public class MyViewController implements Observer,IView,Initializable {
    public MenuItem exitButton;
    public MenuItem SaveButton;
    public MenuItem loadButton;

    private MyViewModel viewmodel;
    private MazeDisplayer mazeDisplayer;


    public void aboutClick(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Generating maze:");
        alert.show();
    }

    public void exitClick(ActionEvent actionEvent) {
        Main.solveSearchProblemServer.stop();
        Main.mazeGeneratingServer.stop();
        Platform.exit();
    }

    public void propertiesClick(ActionEvent actionEvent) {
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Properties");
        //Create view from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Properties.fxml"));
        //Set view in window
        try {
            newWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Launch
        newWindow.show();
    }


    public void NewClick(ActionEvent actionEvent) throws IOException {
        //Create view from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MazeProp.fxml"));
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("New Maze");

        //Set view in window
        try {
            newWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Launch
        newWindow.show();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MazePropController mpc = loader.getController(); //we get the controller (view) from the loader
        mpc.setViewModel(viewModel);


    }


    public void setViewModel(MyViewModel viewModel) {
        this.viewmodel = viewModel;
        this.viewmodel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String)arg;
        switch(change){
            case "maze generated" -> mazeGenerated();
        }
    }

    private void mazeGenerated() {
        mazeDisplayer.drawMaze(viewmodel.getMaze());
    }


    public static void playSound(){
        String path = "./resources/Music/MainMusic.mp3";
        Media media = new Media(new File(path).toURI().toString());
        Main.mediaPlayer = new MediaPlayer(media);
        Main.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        Main.mediaPlayer.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SaveButton.setDisable(true);
        loadButton.setDisable(true);
        playSound();
    }

    public void HelpClicked(ActionEvent actionEvent) {
        Help_Box.display();
    }

    public void AboutClicked(ActionEvent actionEvent) {
        About_Box.display();
    }

    public void ContactusClicked(ActionEvent actionEvent) {
        ContactUsBox.display();
    }
}
