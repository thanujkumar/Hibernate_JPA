package playground.spring.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Paging {
    public final static int DEFAULT_START_INDEX = 0;
    public final static int DEFAULT_PAGE_SIZE = 500;
    public final static int BIG_PAGE_SIZE = Integer.MAX_VALUE;

    //Part of all arg constructor
    private final int startIndex;
    private final int pageSize;


    public final static Paging DEFAULT_PAGING = Paging.builder()
            .startIndex(DEFAULT_START_INDEX)
            .pageSize(DEFAULT_PAGE_SIZE)
            .build();

    public final static Paging BIG_PAGING = Paging.builder()
            .startIndex(DEFAULT_START_INDEX)
            .pageSize(BIG_PAGE_SIZE)
            .build();


    public int getEndIndex() {
        return startIndex + pageSize - 1;
    }
}
