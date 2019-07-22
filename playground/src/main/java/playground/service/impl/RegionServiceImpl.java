package playground.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import playground.dao.RegionDAO;
import playground.model.Region;
import playground.service.RegionService;

import java.util.List;

@Service("regionService")
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDAO regionDAO;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Region> findAll() {
        System.out.println("RegionServiceImpl.findAll isTxActive : " + TransactionSynchronizationManager.isActualTransactionActive());
        applicationEventPublisher.publishEvent("findAll");
        return regionDAO.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)//default required
    public void insert(Region region) {
        System.out.println("RegionServiceImpl.insert isTxActive : " + TransactionSynchronizationManager.isActualTransactionActive());
        applicationEventPublisher.publishEvent("insert");
        regionDAO.save(region);
    }
}