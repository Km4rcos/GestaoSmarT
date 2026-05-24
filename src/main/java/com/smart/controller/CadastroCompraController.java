package com.smart.controller;

import com.smart.dao.CompraDAO;
import com.smart.model.Compra;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class CadastroCompraController {

    @FXML private TextField campoNotaFiscal;
    @FXML private TextField campoDescricao;
    @FXML private DatePicker campoData;
    @FXML private Button botaoSalvar;

    @FXML
    public void salvarCompra() {
        String notaFiscal = campoNotaFiscal.getText();
        String descricao = campoDescricao.getText();
        LocalDate data = campoData.getValue();

        if (notaFiscal.isEmpty() || descricao.isEmpty() || data == null) {
            mostrarAlerta("ERRO", "Preencha todos os campos da nota fiscal!");
            return;
        }

        Compra novaCompra = new Compra(notaFiscal, data, descricao);

        CompraDAO dao = new CompraDAO();
        dao.cadastrarCompra(novaCompra);

        mostrarAlerta("Sucesso", "Compra cadastrada com sucesso!");
        
        voltarParaPainelPrincipal();
    }

    private void voltarParaPainelPrincipal() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/Principal.fxml"));
            Parent telaPrincipal = carregador.load();

            Stage palcoAtual = (Stage) botaoSalvar.getScene().getWindow();
            palcoAtual.setScene(new Scene(telaPrincipal, 800, 600));

        } catch (IOException e) {
            System.out.println("Erro ao voltar para o painel principal: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}