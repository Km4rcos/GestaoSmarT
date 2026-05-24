package com.smart.controller;

import com.smart.dao.UsuarioDAO;
import com.smart.model.Usuario;
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

public class CadastroUsuarioController {

    @FXML private TextField campoNome;
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Button botaoSalvar;

    @FXML
    public void salvarUsuario() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos do usuário!");
            return;
        }

        Usuario novoUsuario = new Usuario(nome, email, senha);
        UsuarioDAO dao = new UsuarioDAO();
        dao.salvarUsuario(novoUsuario);

        mostrarAlerta("Sucesso", "Usuário cadastrado com sucesso no Gestão SmarT!");
        
        irParaLogin();
    }

    @FXML
    public void irParaLogin() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/Login.fxml"));
            Parent telaLogin = carregador.load();

            Stage novoPalco = new Stage();
            novoPalco.setTitle("Gestão SmarT - Login");
            novoPalco.setScene(new Scene(telaLogin, 400, 350));
            novoPalco.show();

            Stage palcoAtual = (Stage) botaoSalvar.getScene().getWindow();
            palcoAtual.close();

        } catch (IOException e) {
            mostrarAlerta("Erro Fatal", "Não foi possível voltar para a tela de login: " + e.getMessage());
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