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

    public ImageImpl(Image image, int id, String fileName, String description) {
        this.image = image;
        this.id = id;
        this.fileName = fileName;
        this.description = description;
    }

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
