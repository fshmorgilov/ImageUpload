package jar;


import jar.model.core.ImageImpl;
import jar.model.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/resources/main-stage.fxml"));
            primaryStage.setTitle("Image View");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args )
    {
        launch(args);

        Database.setupDatabase();
//        Image image = new Image("TestImage.jpg");
//        ImageImpl test = new ImageImpl(image, "","TestImage.jpg");
//        Database.uploadImage(test);
        try {
            Database.retrieveText();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Database.getImageList();
//        Database.uploadText("Sample");


    }

}
