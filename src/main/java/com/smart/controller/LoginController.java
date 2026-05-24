package com.smart.controller;

import com.smart.dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Button botaoEntrar;

    @FXML
    public void fazerLogin() {
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha o e-mail e a senha!");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO(); 
        boolean usuarioValido = dao.buscarUsuario(email, senha);

        if (usuarioValido) {
            abrirTelaPrincipal();
        } else {
            mostrarAlerta("Acesso Negado", "E-mail ou senha incorretos. Tente novamente!");
        }
    }

    private void abrirTelaPrincipal() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/Principal.fxml"));
            Parent telaPrincipal = carregador.load();

            Stage novoPalco = new Stage();
            novoPalco.setTitle("Gestão SmarT - Painel Principal");
            novoPalco.setScene(new Scene(telaPrincipal, 900, 600)); 
            novoPalco.show();

            Stage palcoAtual = (Stage) botaoEntrar.getScene().getWindow();
            palcoAtual.close();

        } catch (IOException e) {
            mostrarAlerta("Erro Fatal", "Não foi possível carregar o painel principal: " + e.getMessage());
        }
    }

    @FXML
    public void abrirTelaCadastro() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/CadastroUsuario.fxml"));
            Parent telaCadastro = carregador.load();

            Stage novoPalco = new Stage();
            novoPalco.setTitle("Gestão SmarT - Novo Usuário");
            novoPalco.setScene(new Scene(telaCadastro, 400, 450));
            novoPalco.show();

            Stage palcoAtual = (Stage) botaoEntrar.getScene().getWindow();
            palcoAtual.close();

        } catch (IOException e) {
            mostrarAlerta("Erro Fatal", "Não foi possível abrir a tela de cadastro: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String message) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }
}