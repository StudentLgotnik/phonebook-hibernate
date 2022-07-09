import entity.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class CreatePhonesHibernate {

    public static void main(String[] args) {
        try(SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Phone.class)
                .buildSessionFactory();
        ) {
            Session session = factory.getCurrentSession();

            Transaction transaction = session.beginTransaction();

            Random rand = new Random();
            for (int i = 0; i < 100_000; i++) {

                Phone student = new Phone();

                student.setPhoneNumber(String.valueOf(rand.nextInt(1000000)));
                student.setOperatorName("Op_" + rand.nextInt(1000000));
                student.setFunds(rand.nextDouble());
                student.setRegistrationDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(rand.nextInt()), ZoneOffset.UTC));
                student.setActivationDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(rand.nextInt()), ZoneOffset.UTC));
                student.setCountryCode(1);
                student.setPerson(2);

                session.persist(student);
            }

            transaction.commit();

        }
    }
}
