package playground.high.performance.persistence.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import playground.dao.InventoryDAO;
import playground.dao.RegionDAO;
import playground.model.Inventory;
import playground.model.Region;

import javax.inject.Inject;
import java.util.List;

@Service("jpaFetchSizeSupport")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JPAFectSizeHandlingSupport {

    private final InventoryDAO inventoryDAO;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Inventory> usingFetchSize_setAsHibernateProperty() {
        return inventoryDAO.findAll();
    }

}