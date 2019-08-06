package playground.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import playground.dao.RegionDAO;
import playground.model.Region;
import playground.service.RegionService;
import playground.spring.paging.Paging;

import javax.inject.Inject;
import java.util.List;

@Service("regionService")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RegionServiceImpl implements RegionService {

    //@Autowired - instead use constructor injection and set as final
    private final RegionDAO regionDAO;

    //@Autowired - instead use constructor injection and set as final
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Region> findAll() {
        System.out.println("RegionServiceImpl.findAll isTxActive : " + TransactionSynchronizationManager.isActualTransactionActive());
        applicationEventPublisher.publishEvent("RegionServiceImpl.findAll");
        return regionDAO.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Region> findAll(Paging paging) {
      return regionDAO.findAll(paging);
    }

    @Transactional(propagation = Propagation.REQUIRED)//default required
    public void insert(Region region) {
        System.out.println("RegionServiceImpl.insert isTxActive : " + TransactionSynchronizationManager.isActualTransactionActive());
        applicationEventPublisher.publishEvent("RegionServiceImpl.insert");
        regionDAO.save(region);
    }

    @Override
    @Transactional
    public Region update(Region region) {
        applicationEventPublisher.publishEvent("RegionServiceImpl.update");
        return regionDAO.update(region);
    }
}
