package jar;


import jar.model.core.ImageIdController;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    private static Logger LOGGER;
//    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
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
        try {
            LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINEST);
            InputStream logConfig = App.class.getClassLoader().getResourceAsStream("log.properties");
            LogManager.getLogManager().readConfiguration(logConfig);
            LOGGER = Logger.getLogger(App.class.getName());
//            Handler fileHandler = new FileHandler(
//                    "C:\\Users\\fshmo\\OneDrive\\Documents\\Logs\\UploadImages\\log.txt"
//                    , 8096
//                    , 1
//                    , true);
            LOGGER.finest(LOGGER.getName());
            LOGGER.info("an info msg");
            LOGGER.warning("a warning msg");
            LOGGER.severe("a severe msg");
//            fileHandler.setLevel(Level.FINEST);
//            fileHandler.setFormatter(new SimpleFormatter());
//            LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).addHandler(fileHandler);
//            LogManager.getLogManager().readConfiguration(new FileInputStream("log.properties"));
//            LogManager.getLogManager().readConfiguration(new FileInputStream("log.properties"));
//            fileHandler.setLevel(Level.ALL);
//            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.finest(e.getStackTrace().toString());
        }
        LOGGER.finest("test");
        ImageIdController.initializeImageIdController();
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
