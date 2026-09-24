import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DBtest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://pgserver:5432/mydatabase";
        String user = "user";
        String password = "password";

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS example");
            String createTableSQL = "CREATE TABLE example ("
                    + "id SERIAL PRIMARY KEY, "
                    + "name VARCHAR(255)"
                    + ")";
            stmt.executeUpdate(createTableSQL);

            String nome = "Wellington Oliveira";

            String insertSQL = "INSERT INTO example (name) VALUES ('"+nome+"')";
            stmt.executeUpdate(insertSQL);

            System.out.println(nome + " adicionado com sucesso");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}