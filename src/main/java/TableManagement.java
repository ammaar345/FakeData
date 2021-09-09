import com.github.javafaker.Faker;

import java.sql.*;

public class TableManagement {


    public static void main(String[] args) {
        Connection connection = null;
        try {
            Faker faker = new Faker();
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            Statement statement = connection.createStatement();
            String sqlInsert = "insert into Pokemon VALUES (?,?,?)";
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists pokemon");
            statement.executeUpdate("create table pokemon (id integer, name string,type string)");
//            statement.executeUpdate("insert into person values(1, 'leo')");

            ResultSet rs = statement.executeQuery("select * from pokemon");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getString("type"));
            }
            for (int i = 0; i < 120000; i++) {
//                int id=faker.random().nextInt(120000)
                int id = faker.random().nextInt(111222);
                String name = faker.pokemon().name();
                String type = faker.name().lastName();
                PreparedStatement st = connection.prepareStatement(sqlInsert);
                st.setInt(1,id );
                st.setString(2, name);
                st.setString(3, type);
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

