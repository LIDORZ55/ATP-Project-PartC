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
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ContactUsBox {
    public static void display(){

        Stage window=new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        //Step A:set an title+ hight and whight of the Box
        window.setTitle("ContactUs");
        window.setHeight(700);
        window.setWidth(1000);
        //Step B:set an image
        Image Contact_Us_Image=null;
        try {
            Contact_Us_Image=new Image(new FileInputStream("resources/images/contact_us.jpg"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image");
        }
        ImageView imageView = new ImageView(Contact_Us_Image);
        imageView.setFitHeight(160);
        imageView.setPreserveRatio(true);

        //Step C:set an Label for the Paragraph
        Label label_information=new Label();
        label_information.setText("Have questions or need to report an issue with our product or service?\nFeel free to send us a message.\nDo not forget to just leave us your personal details so we can contact you back");
        label_information.setFont(new Font("Arial", 20));
        label_information.setTextFill(Color.BLUE);
        //label.setGraphic(imageView);

        //Step D:set an Label for the subject
        Label label_Subject=new Label();
        label_Subject.setText("Enter an subject");
        label_Subject.setFont(new Font("Arial", 15));
        label_Subject.setTextFill(Color.GREEN);

        TextField Subject_Filed = new TextField();
        Subject_Filed.setPrefWidth(500);
        Subject_Filed.setMaxWidth(500);

        Subject_Filed.setMaxHeight(500);
        Subject_Filed.setMaxHeight(500);
        //Step E:set an Label for the subject
        Label label_Context=new Label();
        label_Context.setText("Enter the massage");
        label_Context.setFont(new Font("Arial", 15));
        label_Context.setTextFill(Color.GREEN);

        //Step F: Set the Context_Filed
        TextField Context_Filed = new TextField();
        Context_Filed.setPrefWidth(500);
        Context_Filed.setMaxWidth(500);

        Context_Filed.setMinHeight(250);
        Context_Filed.setMinHeight(250);
        //Step: adding the button close:

        Button closeButton=new Button("Send");

        closeButton.setOnAction(e->SendEmail(Subject_Filed.getText(),Context_Filed.getText(),window));
        VBox layout=new VBox(10);
        Region spacer = new Region();
        spacer.setMinHeight(20);
        spacer.setMinWidth(20);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        layout.getChildren().addAll(imageView,label_information,spacer,label_Subject,Subject_Filed,label_Context,Context_Filed,closeButton);

        layout.setAlignment(Pos.TOP_CENTER);
        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
    public static void SendEmail(String Subject, String txt, Stage window){
        Email email=new Email();
        email.SendEmail(Subject,txt);
        window.close();

    }

}
