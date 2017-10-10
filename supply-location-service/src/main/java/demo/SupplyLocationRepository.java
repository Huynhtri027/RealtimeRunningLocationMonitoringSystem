package demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "supplyLocations")
public interface SupplyLocationRepository extends PagingAndSortingRepository<SupplyLocation, Long> {
    @RestResource(path = "nearLocations")
    SupplyLocation findFirstByLocationNear(@Param("location") Point location);

    @RestResource(path = "cities")
    Page<SupplyLocation> findByCity(@Param("city") String city, Pageable pageable);
}
