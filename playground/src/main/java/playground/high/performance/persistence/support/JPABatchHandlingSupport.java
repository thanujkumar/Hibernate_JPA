package playground.high.performance.persistence.support;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import playground.dao.RegionDAO;
import playground.model.Region;

import javax.inject.Inject;

@Service("jpaBatchSupport")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JPABatchHandlingSupport {
    private final RegionDAO regionDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public void whenBatchNotConfigured_InsertSeparately() {
        for (int i = 0; i < 10; i++) {
            Region region = new Region();
            region.setRegionName("RegionJPABatch" + i);
            region.setCreatedBy("JPA-Batch");
            regionDAO.save(region);
        }
    }

}
