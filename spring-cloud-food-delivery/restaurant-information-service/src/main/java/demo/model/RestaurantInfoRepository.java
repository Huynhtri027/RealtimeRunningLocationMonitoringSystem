package demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "restaurants")
public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, Long> {

    @RestResource(path = "restaurantName")
    RestaurantInfo findByRestaurantName(@Param("restaurantName") String restaurantName);
}
