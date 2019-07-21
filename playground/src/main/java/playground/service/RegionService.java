package playground.service;

import playground.model.Region;

import java.util.List;

public interface RegionService {

    List<Region> findAll();

    void insert(Region region);
}
