import entity.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Map;

public class HibernateFirstLevelCacheDemo {

    public static void main(String[] args) {
        try(SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Phone.class)
                .buildSessionFactory()
        ) {
            Session session = factory.getCurrentSession();

            Transaction transaction = session.beginTransaction();

            for (int i = 0; i < 10; i++) {

                System.out.println("Session entities size: " + getSessionEntities(session).length);

//                Phone phone = session.get(Phone.class, 1001);
                Phone phone = session.getReference(Phone.class, 100_002);
                phone.getId();
                phone.getClass();
                phone.getPhoneNumber();

                System.out.println("Session entities size: " + getSessionEntities(session).length);

                if (i == 5) {
                    session.evict(phone);
//                    session.clear();
                }

                System.out.println("Read phone #" + i + ":\n" + phone);
            }

            transaction.commit();
        }
    }

    private static Map.Entry<Object,org.hibernate.engine.spi.EntityEntry>[] getSessionEntities(Session session) {
        final org.hibernate.engine.spi.SessionImplementor session1 = session.unwrap( org.hibernate.engine.spi.SessionImplementor.class );
        final org.hibernate.engine.spi.PersistenceContext pc = session1.getPersistenceContext();
        return pc.reentrantSafeEntityEntries();
    }
}
