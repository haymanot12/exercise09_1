package cs544.exercise09_1.d;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static final SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder().applySettings(
                        configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main(String[] args) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            for (int x = 0; x < 1000; x++) {
                Owner owner = new Owner("Frank" + x);
                List<Pet> petlist = new ArrayList<Pet>();
                for (int y = 0; y < 10; y++) {
                    Pet pet = new Pet("Garfield" + x + "-" + y);
                    petlist.add(pet);
                }
                owner.setPets(petlist);
                session.persist(owner);
            }

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace(System.err);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        // start time
        long start = System.nanoTime();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Query query=session.createQuery("select distinct o from Owner o left join fetch o.pets");
            List<Owner> ownerlist=query.list();
            for (Owner owner : ownerlist) {
                for (Pet pet : owner.getPets()) {
                    System.out.println("Owner name= " + owner.getName()
                            + "pet name= " + pet.getName());
                }
            }

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace(System.err);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        // stop time
        long stop = System.nanoTime();
        System.out.println("To fetch this data from the database took "
                + (stop - start) / 1000000 + " milliseconds.");
        System.out.println("this is from package d");
        System.exit(0);
    }

}
