package model;

import bean.Aluno;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Essa classe controla o acesso ao banco de dados Teremos os seguintes
 * métodos: inserir, pesquisar*, editar, atualizar, excluir pesquisar (listar e
 * listar todos)
 */
public class Model implements Serializable {

    private Connection connection = null;
    private String statusMessage;

    // construtor
    // esse método é executado toda vez que há uma instanciação da classe
    public Model() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    // Método para listar todos os registros (Menu Listar)
    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT id, ra, nome, curso FROM alunos ORDER BY nome ASC;";

        try (
                Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) { // executa a consulta no banco

            // para cada registro (linha do banco de dados)
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setRa(rs.getString("ra"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCurso(rs.getString("curso"));

                // adiciona o aluno à lista
                alunos.add(aluno);
                //System.out.println(rs.getString("nome"));
            }

        } catch (SQLException ex) {
            // throw new RuntimeException("Falha ao listar os alunos.", ex);
            System.out.println(ex.getMessage());
        }

        // retorna a lista de alunos
        return alunos;
    }

    // Método para pesquisar
    public List<Aluno> pesquisar(Aluno aluno, String tipo) {
        List<Aluno> alunos = new ArrayList<>();
        //String sql = "SELECT id, ra, nome, curso FROM alunos WHERE ra = '321'";

        // chamar a conexão com o banco de dados
        // PreparedStatement serve para aplicar uma proteção ao SQL
        // contra SQL Injection (invasão através de SQL)
        PreparedStatement ps = null;
        String sql = new String();

        try {
            switch (tipo) {
                case "ra":
                    sql = "SELECT * FROM alunos WHERE ra = ?;";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, aluno.getRa());
                    break;

                case "nome":
                    sql = "SELECT * FROM alunos WHERE nome LIKE ? ORDER BY nome ASC;";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, "%" + aluno.getNome() + "%");
                    break;
                case "curso":
                    sql = "SELECT * FROM alunos WHERE curso LIKE ? ORDER BY nome ASC;";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, "%" + aluno.getCurso() + "%");
                    break;
            }

            // executar a consulta no banco
            ResultSet rs = ps.executeQuery();

            // percorrer os dados recuperados, caso existam
            // para cada registro (linha do banco de dados)
            while (rs.next()) {
                aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setRa(rs.getString("ra"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCurso(rs.getString("curso"));

                // adiciona o aluno à lista
                alunos.add(aluno);
            }

        } catch (SQLException sqe) {
            throw new RuntimeException("Falha ao pesquisar." + sqe);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("RA inválido" + nfe);
        }

        // retorna a lista de alunos
        return alunos;
    }

    // método que insere registros no BD
    public void inserir(Aluno aluno) {
        try {
            String sql = "INSERT INTO alunos (ra,nome,curso) VALUES (?,?,?);";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                // atribuição dos valores do objeto "Aluno" ao "ps"
                ps.setString(1, aluno.getRa());
                ps.setString(2, aluno.getNome());
                ps.setString(3, aluno.getCurso());

                // executar a inclusão
                ps.execute();

                // fechar o statement (ps)
                ps.close();
            }
            connection.close(); // fecha a conexão com o banco
            this.statusMessage = "Incluído com sucesso";
            
        } catch (SQLException ex) {
            //this.statusMessage = "Falha ao inserir: " + ex.getMessage();
            this.statusMessage = String.valueOf(ex.getErrorCode());
        }
    }

    @Override // sobrescrita de método
    public String toString() {
        return this.statusMessage;
    }
}
