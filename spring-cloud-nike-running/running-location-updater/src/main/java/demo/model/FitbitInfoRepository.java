package demo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@Component
public interface FitbitInfoRepository extends PagingAndSortingRepository<FitbitInfo, Long>{
    Page<FitbitInfo> findByActivityId(@Param("activityId") String activityId, Pageable pageable);
}
