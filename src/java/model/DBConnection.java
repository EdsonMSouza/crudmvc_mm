package model;

// importação das classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @file DBConnection.java
 * @brief Classe que realiza conexões do tipo Singleton com o banco de dados SQLite
 * @author Edson Melo de Souza 
 *
 */
public class DBConnection {

    // Configurações para conexão com o banco de dados SQLite
    // Driver para o SQLite
    private final String driver = "org.sqlite.JDBC";
    
    // Caminho do arquivo do banco de dados SQLite
    private final String url = "jdbc:sqlite:/home/edson/NetBeansProjects/CRUDMVC/alunos.db";

    // Variável para armazenar a conexão com o banco de dados
    private static DBConnection conexao = null;

    /**
     * Método que prepara a configuração para a conexão
     *
     * @throws SQLException
     */
    private DBConnection() throws SQLException {
        try {
            // Carregar o driver do SQLite
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver não encontrado para SQLite", e);
        }
    }

    /**
     * Realiza a conexão com o banco de dados
     *
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            // Conectar ao banco de dados SQLite
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new SQLException("Falha ao conectar ao banco de dados SQLite: " + e.getMessage(), e);
        }
        return conn;
    }

    /**
     * Retorna uma instância da conexão para utilização (Singleton)
     *
     * @return DBConnection
     * @throws SQLException
     */
    public static DBConnection getInstance() throws SQLException {
        if (conexao == null) {
            conexao = new DBConnection();
        }
        return conexao;
    }
}
