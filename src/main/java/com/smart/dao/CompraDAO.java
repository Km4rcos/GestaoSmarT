package com.smart.dao;

import com.smart.model.Compra;
import com.smart.model.ProdutoComprado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class CompraDAO {

    private Dotenv dotenv = Dotenv.load();

    private String url = dotenv.get("DB_URL");
    private String user = dotenv.get("DB_USER");
    private String password = dotenv.get("DB_PASSWORD");

    public void cadastrarCompra(Compra novaCompra){

        String sql = "insert into compras (nota_fiscal, descricao, data) values (?, ?, ?)";
        String sqlProduto = "insert into produtocomprado (nome, valor, tipo, id_compra) values (?, ?, ?, ?)";
        
        try(Connection conexao = DriverManager.getConnection(url, user, password);
            PreparedStatement comandoCompra = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                comandoCompra.setString(1, novaCompra.getNota_fiscal());
                comandoCompra.setString(2, novaCompra.getDescricao());
                comandoCompra.setObject(3, novaCompra.getData());

                comandoCompra.executeUpdate();

                int idCompraGerado = 0;
                try (ResultSet rs = comandoCompra.getGeneratedKeys()) {
                if(rs.next()){
                    idCompraGerado = rs.getInt(1);
                }

            }

            if (idCompraGerado > 0) {
                try (PreparedStatement comandoProduto = conexao.prepareStatement(sqlProduto)) {
                    
                    for(ProdutoComprado produto : novaCompra.getProdutos()){
                        comandoProduto.setString(1, produto.getNome());
                        comandoProduto.setDouble(2, produto.getValor());
                        comandoProduto.setString(3, produto.getTipo());
                        comandoProduto.setInt(4, idCompraGerado);
                        
                        comandoProduto.executeUpdate();
                    }
                }
            }
            System.out.println("Compra e produtos cadastrados com sucesso no banco!");
    }catch (SQLException e){
            System.out.println("ERRO! " + e.getMessage());
    }

}
public java.util.List<Compra> listarTodas() {
    java.util.List<Compra> lista = new java.util.ArrayList<>();
    String sql = "select * from compras order by id desc";

    try (Connection conexao = DriverManager.getConnection(url, user, password);
         PreparedStatement comando = conexao.prepareStatement(sql);
         ResultSet rs = comando.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String notaFiscal = rs.getString("nota_fiscal");
            String descricao = rs.getString("descricao");
            java.time.LocalDate data = rs.getDate("data").toLocalDate();

            Compra compra = new Compra(notaFiscal, data, descricao);
            compra.setId(id);

            lista.add(compra);
        }

    } catch (SQLException e) {
        System.out.println("Erro ao listar compras: " + e.getMessage());
    }

    return lista;
}
public void cadastrarProdutoIsolado(ProdutoComprado produto, int idCompra) {
    String sql = "insert into produtocomprado (nome, valor, tipo, id_compra) values (?, ?, ?, ?)";
    try (Connection conexao = DriverManager.getConnection(url, user, password);
         PreparedStatement comando = conexao.prepareStatement(sql)) {
        
        comando.setString(1, produto.getNome());
        comando.setDouble(2, produto.getValor());
        comando.setString(3, produto.getTipo());
        comando.setInt(4, idCompra);
        
        comando.executeUpdate();
        System.out.println("Produto avulso inserido!");
    } catch (SQLException e) {
        System.out.println("Erro ao injetar produto: " + e.getMessage());
    }
}
public java.util.List<ProdutoComprado> listarProdutosDaCompra(int idCompra) {
        java.util.List<ProdutoComprado> lista = new java.util.ArrayList<>();
        String sql = "select * from produtocomprado where id_compra = ? order by id desc";

        try (Connection conexao = DriverManager.getConnection(url, user, password);
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setInt(1, idCompra);
            
            try (ResultSet rs = comando.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    double valor = rs.getDouble("valor");
                    String tipo = rs.getString("tipo");

                    ProdutoComprado produto = new ProdutoComprado(valor, nome, tipo);
                    produto.setId(rs.getInt("id"));
                    lista.add(produto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produtos da lista: " + e.getMessage());
        }
        
        return lista;
    }
    public java.util.List<Compra> gerarRelatorioFiltrado(java.time.LocalDate inicio, java.time.LocalDate fim, double min, double max) {
        java.util.List<Compra> lista = new java.util.ArrayList<>();
        
        String sql = "SELECT c.id, c.nota_fiscal, c.descricao, c.data, COALESCE(SUM(p.valor), 0) as total " +
                     "FROM compras c " +
                     "LEFT JOIN produtocomprado p ON c.id = p.id_compra " +
                     "WHERE c.data >= ? AND c.data <= ? " +
                     "GROUP BY c.id, c.nota_fiscal, c.descricao, c.data " +
                     "HAVING COALESCE(SUM(p.valor), 0) >= ? AND COALESCE(SUM(p.valor), 0) <= ? " +
                     "ORDER BY c.data ASC";

        try (Connection conexao = DriverManager.getConnection(url, user, password);
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setDate(1, java.sql.Date.valueOf(inicio));
            comando.setDate(2, java.sql.Date.valueOf(fim));
            comando.setDouble(3, min);
            comando.setDouble(4, max);

            try (ResultSet rs = comando.executeQuery()) {
                while (rs.next()) {
                    Compra compra = new Compra(rs.getString("nota_fiscal"), rs.getDate("data").toLocalDate(), rs.getString("descricao"));
                    compra.setId(rs.getInt("id"));
                    compra.setValorTotal(rs.getDouble("total"));
                    lista.add(compra);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao processar relatório no SGBD: " + e.getMessage());
        }
        return lista;
    }
    public void excluirCompra(int idCompra) {
        String sqlProdutos = "delete from produtocomprado where id_compra = ?";
        String sqlCompra = "delete from compras where id = ?";
        try (Connection conexao = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement cmdProd = conexao.prepareStatement(sqlProdutos)) {
                cmdProd.setInt(1, idCompra);
                cmdProd.executeUpdate();
            }
            try (PreparedStatement cmdCompra = conexao.prepareStatement(sqlCompra)) {
                cmdCompra.setInt(1, idCompra);
                cmdCompra.executeUpdate();
            }
            System.out.println("Compra excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir compra: " + e.getMessage());
        }
    }

    public void excluirProduto(int idProduto) {
        String sql = "delete from produtocomprado where id = ?";
        try (Connection conexao = DriverManager.getConnection(url, user, password);
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idProduto);
            comando.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
    }
}
