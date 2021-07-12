package View;

import File.Creating_File;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MazePropController implements Observer, Initializable {
    public TextField rowTextField;
    public TextField colTextField;
    public Button generateMaze;
    public Pane myPane;
    public BorderPane borderPane;
    public Button SaveButton;
    public Button SolutionButton;
    public MenuItem newButton;
    public MenuItem ExitButton;
    public MenuBar menuBar;
    private Map<String,String> savedMazes = new HashMap<>();

    private MyViewModel viewmodel;
    @FXML
    private MazeDisplayer mazeDisplayer;
    private boolean ending = false;

    public void setViewModel(MyViewModel viewModel) {
        this.viewmodel = viewModel;
        this.viewmodel.addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mazeDisplayer.heightProperty().bind(myPane.heightProperty());
        mazeDisplayer.widthProperty().bind(myPane.widthProperty());
        newButton.setDisable(true);
    }

    public void generateMaze(ActionEvent actionEvent) {
       int rows = checkifValid(rowTextField.getText());
       if(rows == 0 ){
           return;
       }
       int cols = checkifValid(colTextField.getText());
       if(cols ==0){
           return;
       }
       ending = false;
       viewmodel.generateMaze(rows,cols);


    }

    private int checkifValid(String text){
        try{
            int num = Integer.parseInt(text);
            if(num < 1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid integer [1,infnity]");
                alert.show();
            }
            else{
                return num;
            }
        }catch(NumberFormatException nfa){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid integer [1,infnity]");
            alert.show();
        }
        return 0;
    }

    public void keyPressed(KeyEvent keyEvent) {
        viewmodel.movePlayer(keyEvent);
        keyEvent.consume(); //after we done with the keyevent then we will not want other people to use the same key press so we consume it.
    }

    public void zoom(ScrollEvent scrollEvent) {
        if(scrollEvent.isControlDown()){
            double zoomFactor = 1.05;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            borderPane.setScaleX(borderPane.getScaleX() * zoomFactor);
            borderPane.setScaleY(borderPane.getScaleY() * zoomFactor);
        }
    }

    private void setPlayerPostion(int row, int col) {
        mazeDisplayer.setPlayerPosition(row,col);
    }

    public void giveFocus(MouseEvent mouseEvent){
        mazeDisplayer.requestFocus();
    }

    public void movePlayer(MouseEvent mouseEvent) {
        viewmodel.movePlayerWithMouse(mouseEvent,myPane.getHeight(),myPane.getWidth());
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String)arg;
        switch(change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "ending" -> ending();
            case "found solution" -> SolutionFound();
        }
    }



    private void mazeGenerated() {
        mazeDisplayer.drawMaze(viewmodel.getMaze());
    }

    private void playerMoved() {
        setPlayerPostion(viewmodel.getPlayerRow(),viewmodel.getPlayerCol());

    }

    private void ending() {
        if(ending == false){
            ending = true;
            setPlayerPostion(viewmodel.getPlayerRow(),viewmodel.getPlayerCol());
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Ending.fxml"));
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.setTitle("GG");

            //Set view in window
            try {
                newWindow.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Launch
            newWindow.show();
            playSound();
        }
    }

    private void playSound(){
        String path = "./resources/Music/VictoryMusic.mp3";
        Main.mediaPlayer.stop();
        Media media = new Media(new File(path).toURI().toString());
        Main.mediaPlayer = new MediaPlayer(media);
        Main.mediaPlayer.setCycleCount(1);
        Main.mediaPlayer.play();
        Main.mediaPlayer.setOnEndOfMedia(() -> {
            MyViewController.playSound();
        });

    }

    private void SolutionFound() {
        mazeDisplayer.setSolution(viewmodel.getSolution());
    }



    private void setEndingPostion(int endingRow, int endingCol) {
        mazeDisplayer.setEndingPosition(endingRow,endingCol);
    }



    public void SaveButton(ActionEvent actionEvent) {
        if(viewmodel.getMaze() != null){
            Creating_File create = new Creating_File();
            String tempfilename = create.Get_Maze_FileName_withoutSerial(this.viewmodel.getMaze());
            int serialNum = 0;
            if(this.savedMazes.containsKey(tempfilename)){
                serialNum = Integer.parseInt(this.savedMazes.get(tempfilename))+1;
                this.savedMazes.put(tempfilename,String.valueOf(serialNum));
            }
            else{
                this.savedMazes.put(tempfilename,"0");
            }
            viewmodel.solveMazeWithoutWriting();
            create.Creating_Maze_File(this.viewmodel.getMaze(),serialNum);

        }
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

    public void ShowSolution(ActionEvent actionEvent) {
        if(viewmodel.getMaze() != null){
            viewmodel.solveMaze();
        }
    }

    public void LoadButton(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Maze File");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Maze Files","*")
        );
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            String filename = selectedFile.getPath();
            String newfileName = removePath(filename);
            int index = newfileName.charAt(newfileName.length()-1);
            if(filename.contains("maze-")){
                Creating_File create = new Creating_File();
                Maze maze = create.GetMazeObjectByFileName(newfileName);
                this.viewmodel.setMaze(maze);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please choose a valid maze file");
                alert.show();
            }
        }
        ending = false;

    }

    public static String removePath(String str) {
        String newstring ="";
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) =='\\'){
                index = i;
            }
        }
        newstring = str.substring(index+1);
        return newstring;
    }

    public void exitButton(ActionEvent actionEvent) {
        Stage stage = (Stage)menuBar.getScene().getWindow();
        stage.close();
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
