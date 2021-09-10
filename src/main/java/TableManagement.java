import com.github.javafaker.Faker;
import com.github.javafaker.IdNumber;

import java.sql.*;

public class TableManagement {


    public static void main(String[] args) {
        Connection connection = null;
        try {
            Faker faker = new Faker();
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/home/codex-coder/Desktop/FakeData/pokemon.db");
            Statement statement = connection.createStatement();

            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists pokemon");
            statement.executeUpdate("create table pokemon (id integer, name string,type string)");
            statement.executeUpdate("insert into pokemon values(2,'john','read')");
            String sqlInsert = "insert into Pokemon(id,name,type) VALUES (?,?,?)";
//            ResultSet rs = statement.executeQuery("select * from pokemon");
            for (int i = 0; i < 200000; i++) {
//                int id=faker.random().nextInt(120000)
                String id = faker.idNumber().valid();
                String name = faker.pokemon().name();
                String type = faker.name().lastName();
//
                System.out.println(type);
                System.out.println(name);
                System.out.println(id);
                PreparedStatement st = connection.prepareStatement(sqlInsert);
                st.setString(1, id);
                st.setString(2, name);
                st.setString(3, type);
                st.executeUpdate();
//                System.out.println("Success " + statement.executeQuery("select name from pokemon"));
//                st.setString(3, type);
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }
}

