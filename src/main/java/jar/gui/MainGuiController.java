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
    ListView<String> imageNamesListView;
    @FXML
    Button showBtn;
    @FXML
    Button uploadBtn;
    @FXML
    ImageView showImage;
    @FXML
    BorderPane mainPane;


    private ObservableMap<Integer, String> imageIdFileNameMap;
    private ObservableList imageValueList;
    private ObservableList imageKeyList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeImageNameListView();
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
                imageIdFileNameMap.put(imageImplObj.getId(), imageImplObj.getFileName());
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
    @Deprecated
    @FXML
    void showImage() {
    }

    private void initializeImageNameListView() {
        initializeImageLists();
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
    }

    private void initializeImageLists(){
        imageIdFileNameMap = FXCollections.observableMap(Database.retrieveIdFileNameMap());
        imageValueList = FXCollections.observableArrayList(imageIdFileNameMap.values());
        imageKeyList = FXCollections.observableArrayList(imageIdFileNameMap.keySet());
        imageIdFileNameMap.addListener(new MapChangeListener<Integer, String>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends String> change) {
                imageKeyList.add(change.getKey());
                imageValueList.add(change.getValueAdded());
            }
        });
    }

}
