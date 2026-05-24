package com.smart.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Compra {
    private String nota_fiscal;
    private LocalDate data;
    private String descricao;
    private List<ProdutoComprado> produtos;
    private int id;
    private double valorTotal;

    public Compra(String nota_fiscal, LocalDate data, String descricao) {
        this.nota_fiscal = nota_fiscal;
        this.data = data;
        this.descricao = descricao;

        this.produtos = new ArrayList<>();
    }

    public LocalDate getData() {
        return data;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getNota_fiscal() {
        return nota_fiscal;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setNota_fiscal(String nota_fical) {
        this.nota_fiscal = nota_fical;
    }

    public List<ProdutoComprado> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoComprado> produtos) {
        this.produtos = produtos;
    }
    public void adicionarProduto(ProdutoComprado novoProduto){
        this.produtos.add(novoProduto);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
