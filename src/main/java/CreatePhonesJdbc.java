import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class CreatePhonesJdbc {

    private static final String INSERT_INTO_PHONES = "INSERT INTO phonebook_schema.phones (phone_number, operator_name, funds, registration_date, activation_date, country_code_id, person_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static void main(String[] args) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/phonebook_db");
        ds.setUsername("postgres");
        ds.setPassword("admin");

        try (
                Connection connection = ds.getConnection()
        ) {
            Random rand = new Random();
            for (int i = 0; i < 1_000_000; i++) {
                PreparedStatement insertIntoPhones = connection.prepareStatement(INSERT_INTO_PHONES);
                insertIntoPhones.setString(1, String.valueOf(rand.nextInt(1000000)));
                insertIntoPhones.setString(2, "Op_" + rand.nextInt(1000000));
                insertIntoPhones.setDouble(3, rand.nextDouble());
                insertIntoPhones.setObject(4, LocalDateTime.ofInstant(Instant.ofEpochSecond(rand.nextInt()), ZoneOffset.UTC));
                insertIntoPhones.setObject(5, LocalDateTime.ofInstant(Instant.ofEpochSecond(rand.nextInt()), ZoneOffset.UTC));
                insertIntoPhones.setInt(6, 1);
                insertIntoPhones.setInt(7, 1);
                insertIntoPhones.executeUpdate();
                insertIntoPhones.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
