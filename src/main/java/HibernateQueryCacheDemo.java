import entity.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HibernateQueryCacheDemo {

    private static final SessionFactory factory = new Configuration()
            .configure("hibernate_query_cache.cfg.xml")
            .addAnnotatedClass(Phone.class)
            .buildSessionFactory();

    private static volatile int count = 0;

//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            executorService.execute(HibernateQueryCacheDemo::getPhone);
//        }
//        executorService.shutdown();
//    }

    //https://dzone.com/articles/pitfalls-hibernate-second-0
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            getPhone();
            count++;
        }
    }

    public static void getPhone() {

        int phoneId = count > 5 ? 1001 : 1002;
//        int phoneId = 1001;

        Session session = factory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        System.out.println(

                "Current Thread Name: "
                        + Thread.currentThread().getName()
                        + " Current Thread ID: "
                        + Thread.currentThread().getId()
                        + " Session entities size: " + getSessionEntities(session).length);


        Query<Phone> phoneQuery = session.createQuery("from Phone where id = :phoneId", Phone.class);

        phoneQuery.setParameter("phoneId", phoneId);

        phoneQuery.setCacheable(true);


        Phone phone = phoneQuery.getSingleResult();


        System.out.println(

                "Current Thread Name: "
                        + Thread.currentThread().getName()
                        + " Current Thread ID: "
                        + Thread.currentThread().getId()
                        + " Session entities size: " + getSessionEntities(session).length);


        System.out.println(

                "Current Thread Name: "
                        + Thread.currentThread().getName()
                        + " Current Thread ID: "
                        + Thread.currentThread().getId()
                        + " Read phone #" + count + ":\n" + phone

        );

        transaction.commit();
    }

    private static Map.Entry<Object,org.hibernate.engine.spi.EntityEntry>[] getSessionEntities(Session session) {
        final org.hibernate.engine.spi.SessionImplementor session1 = session.unwrap( org.hibernate.engine.spi.SessionImplementor.class );
        final org.hibernate.engine.spi.PersistenceContext pc = session1.getPersistenceContext();
        return pc.reentrantSafeEntityEntries();
    }
}
