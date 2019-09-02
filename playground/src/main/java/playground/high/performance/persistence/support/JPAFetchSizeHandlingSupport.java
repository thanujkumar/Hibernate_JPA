package playground.high.performance.persistence.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import playground.dao.InventoryDAO;
import playground.model.Inventory;

import javax.inject.Inject;
import java.util.List;

@Service("jpaFetchSizeSupport")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JPAFetchSizeHandlingSupport {

    private final InventoryDAO inventoryDAO;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)//REQUIRED, If Required still enrolls into transactions
    public List<Inventory> usingFetchSize_setAsHibernateProperty() {
        return inventoryDAO.findAll();
    }

}