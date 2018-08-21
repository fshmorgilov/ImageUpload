package jar.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainGuiController implements Initializable {

    @FXML
    ListView<String> imageNamesList;
    @FXML
    Button showBtn;
    @FXML
    Button uploadBtn;
    @FXML
    ImageView showImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        imageNamesList = FXCollections.observableList() //--TODO добавить тут коллекцию из базы данных
        Image image = new Image("TestImage.jpg");
        showImage.setImage(image);



    }

    public void displayImage(){
        //--TODO метод, который обращатся к компоненту и показывает изображение
    }

    @FXML
    public void changeImageOnClick(){
        //--TODO отображает выбранное изображение, по нажанию на соответствующую строку в imageameList
    }

}
