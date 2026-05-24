package com.smart.model;

public class ProdutoComprado {
    private double valor;
    private String nome;
    private String tipo;
    private int id;

    public ProdutoComprado(double valor, String nome ,String tipo){
        this.tipo = tipo;
        this.nome = nome;
        this.valor = valor;
    }
    public String getNome() {
        return nome;
    }
    public String getTipo() {
        return tipo;
    }
    public double getValor() {
        return valor;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
