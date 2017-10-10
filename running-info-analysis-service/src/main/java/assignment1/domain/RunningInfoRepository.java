package assignment1.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface RunningInfoRepository extends JpaRepository<RunningInfo, Long> {

    @Transactional
    void deleteByRunningId(@Param("runningId") String runningId);

    Page<RunningInfo> findAll(Pageable pageable);
}
