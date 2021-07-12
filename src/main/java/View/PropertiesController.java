package View;

import Server.Configurations;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PropertiesController implements Initializable {
    //To view the current values
    public TextField threadPoolSizeText;
    public TextField mazeGeneratingAlgoText;
    public TextField mazeSearchingAlgoText;

    //To change the current values
    public TextField ChangeableTextF1;
    public TextField ChangeableTextF2;
    public TextField ChangeableTextF3;
    public Button Button1;
    public Button Button2;
    public Button Button3;


    StringProperty updateThreadPoolSize = new SimpleStringProperty();
    StringProperty updateMazeGeneratingAlgo = new SimpleStringProperty();
    StringProperty updateMazeSearchingAlgo = new SimpleStringProperty();
    StringProperty updateRows = new SimpleStringProperty();
    StringProperty updateCols = new SimpleStringProperty();



    public String getUpdateMazeGeneratingAlgo() {
        return updateMazeGeneratingAlgo.get();
    }

    public void setUpdateMazeGeneratingAlgo(String updateMazeGeneratingAlgo) {
        this.updateMazeGeneratingAlgo.set(updateMazeGeneratingAlgo);
    }

    public String getUpdateMazeSearchingAlgo() {
        return updateMazeSearchingAlgo.get();
    }

    public void setUpdateMazeSearchingAlgo(String updateMazeSearchingAlgo) {
        this.updateMazeSearchingAlgo.set(updateMazeSearchingAlgo);
    }

    public String getUpdateThreadPoolSize() {
        return updateThreadPoolSize.get();
    }

    public void setUpdateThreadPoolSize(String updateThreadPoolSize) {
        this.updateThreadPoolSize.set(""+updateThreadPoolSize);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        threadPoolSizeText.textProperty().bind(updateThreadPoolSize);
        mazeGeneratingAlgoText.textProperty().bind(updateMazeGeneratingAlgo);
        mazeSearchingAlgoText.textProperty().bind(updateMazeSearchingAlgo);
        setUpdateThreadPoolSize(String.valueOf(Configurations.getInstance().getThreadPoolSize()));
        setUpdateMazeGeneratingAlgo(Configurations.getInstance().getMazeGeneratingAlgorithm());
        setUpdateMazeSearchingAlgo(Configurations.getInstance().getMazeSearchingAlgorithm());
    }

    public void ButtonOneClick(ActionEvent actionEvent) {
        try{
            int num = Integer.parseInt(ChangeableTextF1.getText());
            if(num < 1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid integer [1,infnity]");
                alert.show();
            }
            else{
                Configurations.getInstance().setThreadPoolSize(String.valueOf(num));
                setUpdateThreadPoolSize(String.valueOf(Configurations.getInstance().getThreadPoolSize()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully changed the number of threads to "+num);
                alert.show();
            }
        }catch(NumberFormatException nfa){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid integer [1,infnity]");
            alert.show();
        }
    }

    public void ButtonTwoClick(ActionEvent actionEvent) {
        String str = ChangeableTextF2.getText();
        if(str.equals("MyMazeGenerator") || str.equals("SimpleMazeGenerator") || str.equals("EmptyMazeGenerator")){
            Configurations.getInstance().setMazeGeneratingAlgorithm(str);
            setUpdateMazeGeneratingAlgo(Configurations.getInstance().getMazeGeneratingAlgorithm());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully changed the maze generator to "+str);
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(str + " is not a valid maze generator, please enter a valid maze generator (MyMazeGenerator or SimpleMazeGenerator or EmptyMazeGenerator)");
            alert.show();
        }
    }

    public void ButtonThreeClick(ActionEvent actionEvent) {
        String str = ChangeableTextF3.getText();
        if(str.equals("BestFirstSearch") || str.equals("DepthFirstSearch") || str.equals("BreadthFirstSearch")){
            Configurations.getInstance().setMazeSearchingAlgorithm(str);
            setUpdateMazeSearchingAlgo(Configurations.getInstance().getMazeSearchingAlgorithm());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully changed the maze searcher to "+str);
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(str + " is not a valid maze searcher, please enter a valid maze searcher (BestFirstSearch or DepthFirstSearch or BreadthFirstSearch)");
            alert.show();
        }
    }
}
