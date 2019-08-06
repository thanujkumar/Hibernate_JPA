package playground.ws;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playground.model.Region;
import playground.service.RegionService;
import playground.spring.localtx.ex.RequestType;
import playground.spring.paging.SrvPaging;

import javax.inject.Inject;
import java.util.List;

@Service("regionWS")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RegionWS {

    private final RegionService regionService;

    @SrvPaging
    public List<Region> getRegionList(Object empty, RequestType requstType) {
      return regionService.findAll();
    }

}
