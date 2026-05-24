package com.smart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage palcoPrincipal) throws IOException {
        FXMLLoader carregador = new FXMLLoader(App.class.getResource("/com/smart/view/Login.fxml"));
        Parent tela = carregador.load();
        
        Scene cena = new Scene(tela, 400, 350);
        palcoPrincipal.setTitle("Gestão SmarT - MVP");
        palcoPrincipal.setScene(cena);
        palcoPrincipal.show();
    }

    public static void main(String[] args) {
        launch();
        //mvn javafx:run
    }
}