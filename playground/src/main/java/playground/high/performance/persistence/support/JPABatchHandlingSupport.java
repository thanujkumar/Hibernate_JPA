package playground.high.performance.persistence.support;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import playground.dao.RegionDAO;
import playground.model.Region;

import javax.inject.Inject;
import java.util.List;

@Service("jpaBatchSupport")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JPABatchHandlingSupport {
    private final RegionDAO regionDAO;

    /*
       It’s generally a better practice to use Spring Transactional API (than javax.transaction) since it is more natural
       to Spring applications and at the same time it offers more options like timeout, isolation, etc.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void whenBatchNotConfigured_InsertSeparately() {
        for (int i = 0; i < 10; i++) {
            Region region = new Region();
            region.setRegionName("RegionJPABatch" + i);
            region.setCreatedBy("JPA-Batch");
            regionDAO.save(region);
        }
        //To understand what is the impact of readOnly below
        listRegions(); // Pitfall - when calling within class @Transactional proxy doesn't work so below @Transactional is useless
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void listRegions() {
        //What happens if read only operation is done here - check session print
        //OBJECTS ARE IN MANAGED STATE, ANY CHANGE TO OBJECT WILL BE PERSISTED AS THIS METHOD IS CALLED BY ABOVE METHOD
        List<Region> regionList = regionDAO.findAll();
        System.out.println("RegionList size in JPABatchHandlingSupport : " + regionList.size());

        //Change one entity here - If below needs to work comment RegionDAO where query hint readOnly is set, hence below may not work as this object is detached from session
        regionList.get(0).setModifiedBy("e212731");
        System.out.println("Changed modifiedBy for regionId "+ regionList.get(0).getRegionId());
    }

}
