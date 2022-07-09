import entity.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;

public class HibernateNamedQueryDemo {

    public static void main(String[] args) {
        try(SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Phone.class)
                .buildSessionFactory()
        ) {
            Session session = factory.getCurrentSession();

            Transaction transaction = session.beginTransaction();

//            Query<Phone> q = session.createNamedQuery("getAllPhones", Phone.class);
//            List<Phone> phones = q.getResultList();
//            System.out.println("-------All phones named query----------");
//            System.out.println("Phones count: " + phones.size());
//
//            Query<Phone> q2 = session.createNamedQuery("getPhoneById", Phone.class);
//            q2.setParameter("phoneId", 100);
//            Phone phone1 = q2.getSingleResult();
//            System.out.println("-------Phone by id named query----------");
//            System.out.println("Phone: " + phone1.toString());

            Query<Phone> q3 = session.createNamedQuery("getAllPhonesNative", Phone.class);
            List<Phone> phones2 = q3.getResultList();
            System.out.println("-------All phones native query----------");
            System.out.println("Phones count: " + phones2.size());

            Query<Phone> q4 = session.createNamedQuery("getPhoneByIdNative", Phone.class);
            q4.setParameter("phoneId", 100);
            Phone phone2 = q4.getSingleResult();
            System.out.println("-------Phone by id natve query----------");
            System.out.println("Phone: " + phone2.toString());

            transaction.commit();
        }
    }
}
