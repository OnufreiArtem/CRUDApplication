package com.lodbrock;

import com.lodbrock.dao.implementations.ItemMySQLDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;


public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/view/main.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setTitle("CRUD item application");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toString()));

        stage.setOnCloseRequest(windowEvent -> {
            try {
                ItemMySQLDaoImpl.getInstance().getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}