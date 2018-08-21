package jar.model.core;

import javafx.scene.image.Image;

import java.io.FileReader;
import java.util.Optional;

public class ImageImpl {

    private Image image;
    private int id;
    private String fileName;
    private String description;

    public ImageImpl(Image image, String fileName){
        this.image= image;
        this.fileName = fileName;
        this.id = ImageIdController.getNewId();
        this.description = "";
    }

    public ImageImpl(Image image, String description, String fileName) {
        this.image = image;
        this.fileName = fileName;
        this.id = ImageIdController.getNewId();
        this.description = description;
    }

    //--TODO Convert file back to image and feed it to JFX


    public Image getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }
}
