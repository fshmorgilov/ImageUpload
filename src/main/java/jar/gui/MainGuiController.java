package jar.gui;

import jar.model.core.ImageImpl;
import jar.model.database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.image.LookupOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGuiController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(MainGuiController.class.getName());

    @FXML
    ListView<String> imageNamesList;
    @FXML
    Button showBtn;
    @FXML
    Button uploadBtn;
    @FXML
    ImageView showImage;
    @FXML
    BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//--TODO добавить тут коллекцию из базы данных

//        Image image = new Image("TestImage.jpg");
    }

    /**
     * метод, который обращатся к компоненту и показывает изображение
     */

    @FXML
    void uploadImage() {
        //Выводит диалоговое окно, где предлагается выбрать путь//--TODO DONE
        //Выбирает файл, добавляет в коллекцию//--TODO
        //Вызывает базу, добавляет файл//--TODO
        //Profit
        FileChooser chooser = new FileChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File("C:/Users/");
        chooser.setInitialDirectory(defaultDirectory);
        File imageFile = chooser.showOpenDialog(mainPane.getScene().getWindow());
        //
        LOGGER.log(Level.FINE, "Uploading file: {0}", imageFile.getAbsolutePath());
        if (!(("".equals(imageFile.getAbsolutePath())))
                && (imageFile.canRead())) {
            Image image = null;
            try {
                image = new Image(new FileInputStream(imageFile));
                ImageImpl imageImplObj = new ImageImpl(image, "placeholder", imageFile.getName());
            Database.uploadImage(imageImplObj);
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("File not Found");
                alert.showAndWait();
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText("Image is not readable");
            alert.showAndWait();
            LOGGER.log(Level.FINE, "");
        }


//        DirectoryChooser chooser = new DirectoryChooser();
//        chooser.setTitle("JavaFX Projects");
//        File defaultDirectory = new File("c:/dev/javafx");
//        chooser.setInitialDirectory(defaultDirectory);
//        File selectedDirectory = chooser.showDialog(primaryStage);
    }

    /**
     * отображает выбранное изображение, по нажанию на соответствующую строку в imageameList
     */
    @FXML
    void showImage() {
        //--TODO

        //Получить selected Item в ListView//--TODO
        //Получить id по листу
        //Сделать запрос в базу
        //Отобразить изображение в ImageView
    }

//    private void initializeImageNamesList() {
//        ObservableList<String> imageNameList = FXCollections.observableArrayList();
//        imageNameList.addAll(Database.getImageList());
//        imageNameList.addListener(new ListChangeListener<String>() {
//            @Override
//            public void onChanged(Change<? extends String> c) {
//                while (c.next()) {
//                    if (c.wasRemoved()) {
//
//                    }
//                    if (c.wasAdded()) {
//                        //TODO дописать логику вставки изображения
//                    }
//                }
//            }
//        });
//        imageNamesList = new ListView<>(imageNameList);
//    }


}
