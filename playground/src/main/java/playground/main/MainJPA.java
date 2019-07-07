package playground.main;

import playground.model.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class MainJPA {

    public static void main(String[] args) {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        EntityManager entityMgr = entityMgrFactory.createEntityManager();

        Query query = entityMgr.createQuery("select l  from Location l");
        List<Location> nameList = query.getResultList();
        for (Location l : nameList) {
            System.out.println("First Name :-" + l.getAddress());
        }

        entityMgr.close();
        entityMgrFactory.close();
    }
}
