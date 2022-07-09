import entity.Phone;
import net.sf.ehcache.CacheManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HibernateSecondLevelCacheDemo {

    private static final SessionFactory factory = new Configuration()
            .configure("hibernate_second_lvl.cfg.xml")
            .addAnnotatedClass(Phone.class)
            .buildSessionFactory();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(HibernateSecondLevelCacheDemo::getPhone);
        }
        System.out.println("Done!");
        executorService.shutdown();
    }

    public static void getPhone() {

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Phone phone = session.get(Phone.class, 1001);
        long size = session.getSessionFactory().getStatistics().getSecondLevelCacheHitCount();
        System.out.println("Second level cache hit count: " + size);
        session.flush();session.close();
    }
}
