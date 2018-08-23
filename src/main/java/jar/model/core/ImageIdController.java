package jar.model.core;

import jar.model.database.Database;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ImageIdController {
    private static final Logger LOGGER = Logger.getLogger(ImageIdController.class.getName());

    private static ArrayList<Integer> imageIdList = new ArrayList<>();

    public static int getNewId() {
        if (imageIdList.isEmpty()) {
            imageIdList.add(1);
            LOGGER.log(Level.FINE, "Created new ImageId list");
            return 1;
        } else {
            int x = (imageIdList.get(imageIdList.size()));
            imageIdList.add(++x);
            LOGGER.log(Level.FINE, "generated id: {0}", ++x);
            return ++x;
        }
    }

    public static void removeId(int id) {
        imageIdList.remove(id);
        LOGGER.log(Level.FINE, "Removed id: {0}", id);
    }

    public static void initializeImageIdController() {
        imageIdList.addAll(Database.getImageIdList());
        LOGGER.log(Level.FINE, "Id list size: {0}", imageIdList.size());
    }
}
