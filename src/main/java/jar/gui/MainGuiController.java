package jar.gui;

import jar.model.database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
//--TODO добавить тут коллекцию из базы данных

        Image image = new Image("TestImage.jpg");
    }
    /**метод, который обращатся к компоненту и показывает изображение*/

    @FXML
    void uploadImage(){
        //Выводит диалоговое окно, где предлагается выбрать путь//--TODO
        //Выбирает файл, добавляет в коллекцию//--TODO
        //Вызывает базу, добавляет файл//--TODO
        //Profit

//        TODO диалог с выбором директории
//        DirectoryChooser chooser = new DirectoryChooser();
//        chooser.setTitle("JavaFX Projects");
//        File defaultDirectory = new File("c:/dev/javafx");
//        chooser.setInitialDirectory(defaultDirectory);
//        File selectedDirectory = chooser.showDialog(primaryStage);
    }
    /**отображает выбранное изображение, по нажанию на соответствующую строку в imageameList*/
    @FXML
    void showImage(){
        //--TODO

        //Получить selected Item в ListView//--TODO
        //Получить id по листу
        //Сделать запрос в базу
        //Отобразить изображение в ImageView
    }

    private void initializeImageNamesList(){
        ObservableList<String> imageNameList = FXCollections.observableArrayList();
        imageNameList.addAll(Database.getImageList());
        imageNameList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                while (c.next())
                {
                    if(c.wasRemoved()){

                    }
                    if(c.wasAdded()){
                        //TODO дописать логику вставки изображения
                    }
                }
            }
        });
        imageNamesList = new ListView<>(imageNameList);
    }



}
