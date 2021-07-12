package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EndingController {

    public Button closeButton;

    public void buttonClicked(ActionEvent actionEvent) {
        Main.mediaPlayer.stop();
        MyViewController.playSound();
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
}
