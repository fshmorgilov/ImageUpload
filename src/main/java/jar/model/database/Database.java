package jar.model.database;

import jar.model.core.ImageImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;

import java.sql.SQLException;
import java.util.List;

public class Database {

    private static final String DRV_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/?user=root&password=root&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String NEW_CONN_STRING = "jdbc:mysql://localhost:3306/";
    private static final String USE_DB = "USE DB;";

    public static Connection getConnection() throws SQLException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement());
        }
        /**Загружаем драйвер*/
        try {
            Class.forName(DRV_NAME);
            System.out.println("Driver retrieved");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
            System.exit(-1);
        }
        /**Подплючаемся к базе*/
        Connection connection = DriverManager.getConnection(CONN_STRING);
        System.out.println("Connection established");
//        Connection connection = DriverManager.getConnection(NEW_CONN_STRING, "root", "root");
//        connection.setAutoCommit(false);
        /**Получить результаты работы getConnection*/
//        try {
//            ResultSet resultSet = connection.getMetaData().getCatalogs();
//            while (resultSet.next()) System.out.println(resultSet.getString("TABLE_CAT"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return connection;

    }

    public static void requestImage(int id) {
        //Получаем изображение из базы
        /**Ну и получаем данные НАКОНЕЦТО*/
        //PreparedStatement
        try {
            Statement st = Database.getConnection().createStatement();
            st.executeUpdate(USE_DB);
//            st.executeUpdate("CREATE table persons (id int(4) not null primary key , name varchar(32), age int(3))");
//            st.executeUpdate("INSERT into persons (id,name,age) values (11,'Name3', 38)");
//            ResultSet rs = st.executeQuery("SELECT * FROM PERSONS ORDER BY NAME desc ");
//            while (rs.next()) {
//                System.out.println(rs.getString("id")
//                        + "-"
//                        + rs.getString("name")
//                        + "-"
//                        + rs.getString("age"));
//            }
//            PreparedStatement st2 = Database.getConnection().prepareStatement("SELECT * FROM ? WHERE age = ?");
//            st2.setString(1, "persons");
//            st2.setInt(2, 30);
            getConnection().commit();

            //return image
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error in Request Image");
        }
    }

    //--TODO  доделать, добавить результат list
    public static void getImageList() {
        //Добавляем изображение в базу по получению из интерфейса
        String requestImageListStmtStr = "SELECT ";

        //--TODO add logic


        try {
            PreparedStatement requestImageListStmt = Database.getConnection().prepareStatement(requestImageListStmtStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void setupDatabase() {
        String createTableText = "CREATE TABLE IF NOT EXISTS `TEXT_TEST` (`id` INT(4) NOT NULL PRIMARY KEY, `text` VARCHAR(40) NULL)";
        String createTableImages = "CREATE TABLE IF NOT EXISTS `images` (`id` INT(4) NOT NULL PRIMARY KEY, `image_blob` BLOB NULL,`file_name` VARCHAR(40) NULL,`description` VARCHAR(40) NULL)";
//        String createTableImages = "USE `db`;" +
//                "CREATE TABLE IF NOT EXISTS `images` (" +
//                "`id` INT(4) NOT NULL PRIMARY KEY," +
//                "`image_blob` BLOB NULL," +
//                "`file_name` VARCHAR(40) NULL," +
//                "`description` VARCHAR(40) NULL" +
//                ");";
//        String createTableImages = "CREATE TABLE 'images' " +
//                "('id' int(4) NOT NULL PRIMARY KEY" +
//                ", 'image_blob' blob" +
//                ", 'file_name' varchar(40))";
//
        try {

            Statement st = Database.getConnection().createStatement();
//            st.executeUpdate("CREATE DATABASE db");
            st.execute(USE_DB);
            st.executeUpdate(createTableImages);
            st.executeUpdate(createTableText);
//            getConnection().commit();

            //--TODO ВЫКЛЮЧИТЬ AUTOCOMMIT
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to create tables");
        }
//                    st.executeUpdate("CREATE table persons (id int(4) not null primary key , name varchar(32), age int(3))");
//            st.executeUpdate("INSERT into persons (id,name,age) values (11,'Name3', 38)");

    }

    public static void uploadImage(ImageImpl imageImpl) {

        String uploadImageStmntString = "INSERT INTO DB.IMAGES VALUES (?, ?, ?, ?)";
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
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error in getImageList");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void uploadText(String text) {

        String uploadTextStmntString = "INSERT INTO DB.TEXT_TEST (id, text) VALUES (?, ?)";
        try {
            PreparedStatement uploadTextStatement = Database.getConnection().prepareStatement(uploadTextStmntString);
            uploadTextStatement.setInt(1, 1);
            uploadTextStatement.setString(2, text);
            uploadTextStatement.executeUpdate();
            getConnection().commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
