package com.smart.controller;

import com.smart.dao.CompraDAO;
import com.smart.model.Compra;
import com.smart.model.ProdutoComprado;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class CadastroProdutoController {

    @FXML private Label labelIdentificador;
    @FXML private TextField campoNome;
    @FXML private TextField campoValor;
    @FXML private TextField campoTipo;

    @FXML private TableView<ProdutoComprado> tabelaProdutos;
    @FXML private TableColumn<ProdutoComprado, String> colunaNome;
    @FXML private TableColumn<ProdutoComprado, Double> colunaValor;
    @FXML private TableColumn<ProdutoComprado, String> colunaTipo;

    private Compra compraPai;

    public void setCompraSelected(Compra compra) {
        this.compraPai = compra;
        this.labelIdentificador.setText("Compra vinculada: " + compra.getDescricao());
    }
    
    public void setCompraSelecionada(Compra compra) {
        setCompraSelected(compra);
        atualizarTabelaProdutos();
    }

    @FXML
    public void adicionarProdutoItem() {
        String nome = campoNome.getText();
        String tipo = campoTipo.getText();
        String valorTexto = campoValor.getText();

        if (nome.isEmpty() || tipo.isEmpty() || valorTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os dados do produto!");
            return;
        }

        double valor = Double.parseDouble(valorTexto);

        ProdutoComprado produto = new ProdutoComprado(valor, nome, tipo);

        CompraDAO dao = new CompraDAO();
        dao.cadastrarProdutoIsolado(produto, compraPai.getId());

        mostrarAlerta("Sucesso", "Produto acoplado com sucesso!");

        campoNome.clear();
        campoValor.clear();
        campoTipo.clear();
        
        atualizarTabelaProdutos();
    }

    @FXML
    public void voltar() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/Principal.fxml"));
            Parent root = carregador.load();
            Stage stage = (Stage) labelIdentificador.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            System.out.println("Erro ao retornar: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    private void atualizarTabelaProdutos() {
        if (compraPai != null) {
            CompraDAO dao = new CompraDAO();
            List<ProdutoComprado> produtosExistentes = dao.listarProdutosDaCompra(compraPai.getId());
            tabelaProdutos.setItems(FXCollections.observableArrayList(produtosExistentes));
        }
    }
    @FXML
    public void removerProduto() {
        ProdutoComprado produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um produto na tabela para remover!");
            return;
        }

        boolean confirmou = confirmarExclusao("Atenção", "Tem certeza que deseja remover '" + produtoSelecionado.getNome() + "' da lista de compras?");
        
        if (confirmou) {
            CompraDAO dao = new CompraDAO();
            dao.excluirProduto(produtoSelecionado.getId());
            atualizarTabelaProdutos();
        }
    }

    private boolean confirmarExclusao(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        java.util.Optional<javafx.scene.control.ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == javafx.scene.control.ButtonType.OK;
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}