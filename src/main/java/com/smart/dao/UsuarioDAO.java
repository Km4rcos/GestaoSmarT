package com.smart.dao;

import com.smart.model.Usuario;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class UsuarioDAO {

    private Dotenv dotenv = Dotenv.load();

    private String url = dotenv.get("DB_URL");
    private String user = dotenv.get("DB_USER");
    private String password = dotenv.get("DB_PASSWORD");

    public boolean buscarUsuario(String email, String senha){
        String sql = "select * from usuarios where email = ? and senha = ?";

        try(Connection conexao = DriverManager.getConnection(url, user, password);
            PreparedStatement comando = conexao.prepareStatement(sql)){

                comando.setString(1, email);
                comando.setString(2, senha);

                try (ResultSet rs = comando.executeQuery()) {
                    if(rs.next()){
                        return true;
                    }
                }

            } catch (SQLException e){
            System.out.println("ERRO! " + e.getMessage());
            }
        
        return false;

    }

    public void salvarUsuario(Usuario novoUsuario){

        String sql = "insert into usuarios (nome, email, senha) values (?, ?, ?)";

        try(Connection conexao = DriverManager.getConnection(url, user, password);
            PreparedStatement comando = conexao.prepareStatement(sql)){

                comando.setString(1, novoUsuario.getNome());
                comando.setString(2, novoUsuario.getEmail());
                comando.setString(3, novoUsuario.getSenha());

                comando.executeUpdate();

                System.out.println("Usuario cadastrada com sucesso no banco!");
            }catch (SQLException e){
                System.out.println("ERRO! " + e.getMessage());
            }

    }
}
