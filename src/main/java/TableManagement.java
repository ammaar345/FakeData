import com.github.javafaker.Faker;
import com.github.javafaker.IdNumber;

import java.sql.*;

public class TableManagement {


    public static void main(String[] args) {
        Connection connection = null;
        try {
            Faker faker = new Faker();
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists pokemon");
            statement.executeUpdate("create table if not exists pokemon (id integer not null primary key, name string,location string)");
            String sqlInsert = "insert into pokemon(id,name,location) VALUES (?,?,?)";
//            statement.executeUpdate("CREATE INDEX if not exists location_index ON pokemon (location)");
//            statement.executeUpdate("CREATE INDEX if not exists id_index ON pokemon (id)");
//            statement.executeUpdate("CREATE INDEX if not exists name_index ON pokemon (name)");
            statement.executeUpdate("Drop INDEX if exists pokemon.location_index");
            statement.executeUpdate("Drop INDEX if exists pokemon.id_index");
            statement.executeUpdate("Drop INDEX if exists pokemon.name_index");
//5 seconds without any index
            //7 seconds with an index
            //11 seconds with 2 indexes
//10/11 seconds with three indexes
            PreparedStatement st = connection.prepareStatement(sqlInsert);
            for (int i = 0; i < 30; i++) {
                String id = faker.idNumber().valid();
                String name = faker.pokemon().name();
                String location = faker.pokemon().location();
//
//                System.out.println(location);
//                System.out.println(name);
//                System.out.println(id);
//                st.setString(1, id);
                st.setString(2, name);
                st.setString(3, location);

                st.executeUpdate();
            }
            System.out.println("complete");
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

}


