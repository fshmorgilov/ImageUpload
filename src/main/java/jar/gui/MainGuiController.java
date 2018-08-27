package jar.gui;

import jar.model.core.ImageImpl;
import jar.model.database.Database;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import javax.xml.crypto.Data;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGuiController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(MainGuiController.class.getName());

    @FXML
    ListView<String> imageNamesListView;
    @FXML
    Button showBtn;
    @FXML
    Button uploadBtn;
    @FXML
    ImageView showImage;
    @FXML
    BorderPane mainPane;


    ObservableList<String> imageNameList = FXCollections.observableArrayList();
    ObservableMap<Integer, String> imageIdFileNameMap = FXCollections.observableMap(Database.retrieveIdFileNameMap());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeImageNameList();
    }

    /**
     * метод, который обращатся к компоненту и показывает изображение
     */

    @FXML
    void uploadImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File("C:/Users/");
        chooser.setInitialDirectory(defaultDirectory);
        File imageFile = chooser.showOpenDialog(mainPane.getScene().getWindow());
        LOGGER.log(Level.FINE, "Uploading file: {0}", imageFile.getAbsolutePath());
        if (!(("".equals(imageFile.getAbsolutePath())))
                && (imageFile.canRead())) {
            Image image = null;
            try {
                image = new Image(new FileInputStream(imageFile));
                ImageImpl imageImplObj = new ImageImpl(image, "placeholder", imageFile.getName());
                Database.uploadImage(imageImplObj);
                imageNameList.add(imageFile.getName());
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("File not Found");
                alert.showAndWait();
                e.printStackTrace();
                LOGGER.warning("File not found");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText("Image is not readable");
            alert.showAndWait();
            LOGGER.log(Level.FINE, "");
        }
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


    private void initializeImageNameList() {
        ObservableList imageValueList = FXCollections.observableArrayList(imageIdFileNameMap.values());
        ObservableList imageKeyList = FXCollections.observableArrayList(imageIdFileNameMap.keySet());
        imageNamesListView.setItems(imageValueList);
        imageNamesListView.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                int id = (int) imageKeyList.get(imageNamesListView.getSelectionModel().getSelectedIndex());

                try {
                    Image image = Database.retrieveImage(id).getImage();
                    showImage.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                    //TODO
                }
                System.out.println(id);
                System.out.println(imageNamesListView.getSelectionModel().getSelectedItem());
                System.out.println(imageNamesListView.getSelectionModel().getSelectedIndex());
            }
        });

//        imageNamesListView.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent event) {
//                Integer id = imageNamesListView.getSelectionModel().getSelectedIndex();
//                if (!id.equals(null)) {
//                    try {
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (SQLException e) {
//                        LOGGER.severe(e.getMessage());
//                    }
//                }
//            }
//        });
    }


    private void initializeImageNamesList() {
        imageNameList.addAll(Database.getImageList());
        imageNameList.forEach(LOGGER::info);
        imageNameList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                while (c.next()) {
                    if (c.wasRemoved()) {
                        //TODO
                    }
                    if (c.wasAdded()) {

                    }
                }
            }
        });
    }


}
