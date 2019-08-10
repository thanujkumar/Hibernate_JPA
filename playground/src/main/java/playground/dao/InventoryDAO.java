package playground.dao;

import org.springframework.stereotype.Repository;
import playground.model.Inventory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class InventoryDAO {


    @PersistenceContext
    private EntityManager entityManager;

    public List<Inventory> findAll() {
        return entityManager.createNamedQuery(Inventory.QUERY_ALL).getResultList();
    }
}
