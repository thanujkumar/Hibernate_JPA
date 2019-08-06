package playground.service;

import playground.model.Region;
import playground.spring.paging.Paging;

import java.util.List;

public interface RegionService {

    List<Region> findAll();

    List<Region> findAll(Paging paging);

    void insert(Region region);

    Region update(Region region);
}
