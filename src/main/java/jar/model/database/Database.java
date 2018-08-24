package jar.model.database;

import jar.model.core.ImageImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Database {

    private static Logger LOGGER = Logger.getLogger(Database.class.getName());

    private static final String DRV_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/?user=root&password=root&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String NEW_CONN_STRING = "jdbc:mysql://localhost:3306/";
    private static final String USE_DB = "USE DB;";

    private static Connection getConnection() throws SQLException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement());
        }
        /**Загружаем драйвер*/
        try {
            Class.forName(DRV_NAME);
            LOGGER.finest("Driver retrieved: " +"DRV_NAME" );
        } catch (ClassNotFoundException ex) {
            LOGGER.severe("Driver not found \n" + ex.getMessage());
            System.exit(-1);
        }
        /**Подплючаемся к базе*/
        Connection connection = DriverManager.getConnection(CONN_STRING);
        LOGGER.finest("Connection to DB fine");
        return connection;

    }

    /**
     * Получаем изображение из базы
     */
    public static ImageImpl retrieveImage(int id) throws IOException, SQLException {
        String retrieveImage = " SELECT * FROM db.images where id = ?";
        PreparedStatement st = Database.getConnection().prepareStatement(retrieveImage);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        int index = rs.getInt(1);
        Blob imageBlob = rs.getBlob(2);
        BufferedImage bufferedImage = ImageIO.read(imageBlob.getBinaryStream());
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        String fileName = rs.getString(3);
        String description = rs.getString(3);
        LOGGER.fine("Retrieve image: " + fileName);
        return new ImageImpl(image, index, fileName, description);
    }

    /**
     * Получаем список названий файлов изображений из базы
     */
    public static List<String> getImageList() {
        String requestImageListStmtStr = "SELECT FILE_NAME from db.images;";
        List<String> imageNamesList = new ArrayList<>();
        try {
            PreparedStatement requestImageListStmt = Database.getConnection().prepareStatement(requestImageListStmtStr);
            ResultSet rs = requestImageListStmt.executeQuery();
            while (rs.next()) {
                imageNamesList = Stream.of(rs.getString(1)).collect(Collectors.toList());
            }
            return imageNamesList;

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.severe("Error in getImageList\n" + e.getMessage());
            //FIXME прожовывает эксепшн
            return imageNamesList;
        }
    }

    /**
     * Получаем список Id файлов изображений из базы
     */
    public static ArrayList<Integer> getImageIdList() {
        String requestImageIdStmtStr = "SELECT id from db.images;";
        ArrayList<Integer> imageIdList = new ArrayList<>();
        try {
            PreparedStatement requestImageIdListStmt = Database.getConnection().prepareStatement(requestImageIdStmtStr);
            ResultSet rs = requestImageIdListStmt.executeQuery();
            while (rs.next()) {
                imageIdList.addAll(Stream.of(rs.getInt(1)).collect(Collectors.toList()));
            }
            return imageIdList;

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.severe("Error in getImageIdList\n" + e.getMessage());
            return imageIdList;
        }
    }

    /**
     * Развертывание таблиц в базе
     */
    public static void setupDatabase() {
        String createTableText = "CREATE TABLE IF NOT EXISTS `TEXT_TEST` (`id` INT(4) NOT NULL PRIMARY KEY, `text` VARCHAR(40) NULL)";
        String createTableImages = "CREATE TABLE IF NOT EXISTS `images` (`id` INT(4) NOT NULL PRIMARY KEY, `image_blob` BLOB NULL,`file_name` VARCHAR(40) NULL,`description` VARCHAR(40) NULL)";
        try {

            Statement st = Database.getConnection().createStatement();
            st.execute(USE_DB);
            st.executeUpdate(createTableImages);
            st.executeUpdate(createTableText);
            LOGGER.finest("tables updated");
            //--TODO ВЫКЛЮЧИТЬ AUTOCOMMIT
        } catch (Exception ex) {
            LOGGER.severe("Failed to create tables. \n" +ex.getMessage());
        }
//                    st.executeUpdate("CREATE table persons (id int(4) not null primary key , name varchar(32), age int(3))");
//            st.executeUpdate("INSERT into persons (id,name,age) values (11,'Name3', 38)");
    }

    /**
     * Добавляем изображение в базу по получению из интерфейса
     */
    public static void uploadImage(ImageImpl imageImpl) {

        String uploadImageStmntString = " insert INTO db.images (id, image_blob, file_name, description ) VALUES (?, ?, ?, ?)";
        //запросить полный список изображений из базы
        try {
            Statement useDB = getConnection().createStatement();
            PreparedStatement uploadImageStmt = Database.getConnection().prepareStatement(uploadImageStmntString);
//            Blob imageBlob = getConnection().createBlob();
            ByteArrayOutputStream byteArraystr = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(imageImpl.getImage(), null), "jpg", byteArraystr);
            uploadImageStmt.setInt(1, imageImpl.getId());
            uploadImageStmt.setBytes(2, byteArraystr.toByteArray());
            uploadImageStmt.setString(3, imageImpl.getFileName());
            uploadImageStmt.setString(4, imageImpl.getDescription());
//            st.execute(USE_DB);
            uploadImageStmt.executeUpdate();
            getConnection().commit();
            LOGGER.finest("Writing image to database: " + imageImpl.getFileName() + "\nId in Database :" + imageImpl.getId());
        } catch (SQLException ex) {
            LOGGER.severe("Error in uploading image to SQL");
            LOGGER.severe(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe("Error: file is corrupted \n" + ex.getMessage());
        }
    }

    public static void uploadText(String text) {

        String uploadTextStmntString = "INSERT INTO db.text_test (id, text) VALUES (?, ?)";
        try {
            PreparedStatement uploadTextStatement = Database.getConnection().prepareStatement(uploadTextStmntString);
            uploadTextStatement.setInt(1, 3);
            uploadTextStatement.setString(2, text);
            uploadTextStatement.executeUpdate();
//            getConnection().commit(); //--TODO
        } catch (SQLException ex) {
            LOGGER.warning(ex.getMessage());
        }
    }

    public static ResultSet retrieveText() throws SQLException {
        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM db.text_test ");
        while (rs.next()) {
            System.out.println(rs.getString("id")
                    + "-"
                    + rs.getString("text"));
        }
        return rs;
    }

}
