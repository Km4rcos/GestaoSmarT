package com.smart.controller;

import com.smart.dao.CompraDAO;
import com.smart.model.Compra;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RelatorioGraficoController {

    @FXML private DatePicker campoDataInicio;
    @FXML private DatePicker campoDataFim;
    @FXML private TextField campoValorMin;
    @FXML private TextField campoValorMax;
    @FXML private Label labelTotal;
    
    @FXML private BarChart<String, Number> graficoCompras;

    @FXML
    public void processarRelatorio() {
        LocalDate inicio = campoDataInicio.getValue();
        LocalDate fim = campoDataFim.getValue();
        String txtMin = campoValorMin.getText();
        String txtMax = campoValorMax.getText();

        if (inicio == null || fim == null || txtMin.isEmpty() || txtMax.isEmpty()) {
            mostrarAlerta("Filtros Incompletos", "Por favor, defina o período de datas e os valores mínimo e máximo.");
            return;
        }

        double min = Double.parseDouble(txtMin);
        double max = Double.parseDouble(txtMax);

        CompraDAO dao = new CompraDAO();
        List<Compra> comprasFiltradas = dao.gerarRelatorioFiltrado(inicio, fim, min, max);

        graficoCompras.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        double somaGeral = 0;

        for (Compra c : comprasFiltradas) {
            serie.getData().add(new XYChart.Data<>(c.getDescricao() + "\n(" + c.getData() + ")", c.getValorTotal()));
            somaGeral += c.getValorTotal();
        }

        graficoCompras.getData().add(serie);
        labelTotal.setText(String.format("TOTAL NO PERÍODO: R$ %.2f", somaGeral));
    }

    @FXML
    public void voltar() {
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/com/smart/view/Principal.fxml"));
            Parent root = carregador.load();
            Stage stage = (Stage) labelTotal.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            System.out.println("Erro ao retornar: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}