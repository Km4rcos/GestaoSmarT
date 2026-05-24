package com.smart.controller;

import com.smart.dao.CompraDAO;
import com.smart.model.Compra;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PrincipalController {

    @FXML private BorderPane painelPrincipal;
    @FXML private TableView<Compra> tabelaCompras;
    @FXML private TableColumn<Compra, String> colunaDescricao;

    private Node telaRelatorioOriginal;

    @FXML
    public void initialize() {
        telaRelatorioOriginal = painelPrincipal.getCenter();
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabelaCompras.setRowFactory(tv -> {
            TableRow<Compra> linha = new TableRow<>();
            linha.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!linha.isEmpty())) {
                    Compra compraSelecionada = linha.getItem();
                    abrirGerenciadorProdutos(compraSelecionada);
                }
            });
            return linha;
        });

        atualizarTabela();
    }

    public void abrirGerenciadorProdutos(Compra compraSelecionada) {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/CadastroProduto.fxml"));
            Parent telaProdutos = carregador.load();

            CadastroProdutoController controller = carregador.getController();
            controller.setCompraSelecionada(compraSelecionada);

            painelPrincipal.setCenter(telaProdutos);

        } catch (IOException e) {
            System.out.println("Erro ao abrir gerenciador de produtos: " + e.getMessage());
        }
    }

    @FXML 
    public void abrirRelatorioGrafico() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/RelatorioGrafico.fxml"));
            Parent telaRelatorio = carregador.load();
            
            painelPrincipal.setCenter(telaRelatorio);
        } catch (IOException e) {
            System.out.println("Erro ao abrir relatório gráfico: " + e.getMessage());
        }
    }

    @FXML 
    public void exibirCadastroCompra() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/CadastroCompra.fxml"));
            painelPrincipal.setCenter(carregador.load());
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @FXML 
    public void exibirRelatorioCompras() {
        painelPrincipal.setCenter(telaRelatorioOriginal);
        atualizarTabela();
    }

    @FXML 
    public void fazerLogout() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/Login.fxml"));
            Stage palco = (Stage) painelPrincipal.getScene().getWindow();
            palco.setScene(new Scene(carregador.load(), 400, 350));
            palco.setTitle("Gestão SmarT - Login");
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarTabela() {
        CompraDAO dao = new CompraDAO();
        List<Compra> listaDeCompras = dao.listarTodas();
        tabelaCompras.setItems(FXCollections.observableArrayList(listaDeCompras));
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
    @FXML
    public void removerCompra() {
        Compra compraSelecionada = tabelaCompras.getSelectionModel().getSelectedItem();

        if (compraSelecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma compra na tabela para excluir!");
            return;
        }

        boolean confirmou = confirmarExclusao("Atenção", "Tem certeza que deseja apagar a compra '" + compraSelecionada.getDescricao() + "' e TODOS os produtos dentro dela?\nEsta ação não pode ser desfeita.");
        
        if (confirmou) {
            com.smart.dao.CompraDAO dao = new com.smart.dao.CompraDAO();
            dao.excluirCompra(compraSelecionada.getId());
            atualizarTabela();
        }
    }

    private boolean confirmarExclusao(String titulo, String mensagem) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        java.util.Optional<javafx.scene.control.ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == javafx.scene.control.ButtonType.OK;
    }
}