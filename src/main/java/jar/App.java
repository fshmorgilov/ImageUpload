package jar;


import jar.model.core.ImageImpl;
import jar.model.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        Thread dbThread = new Thread(() -> {
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
        });
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(dbThread);
        launch(args);



    }

}
