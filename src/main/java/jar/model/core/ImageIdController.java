package jar.model.core;

import java.util.ArrayList;

public abstract class ImageIdController {

    private static ArrayList<Integer> imageIdList = new ArrayList<>();
    //--TODO Array with fileNAmes to be displayed in GUI, observable

    public static int getNewId() {
        if (imageIdList.isEmpty()) {
            imageIdList.add(1);
            return 1;
        } else {
            int x =  (imageIdList.get(imageIdList.size()));
            imageIdList.add(++x);
            return ++x;
        }

    }

    public static void removeId(int id) {
        imageIdList.remove(id);
    }
}
