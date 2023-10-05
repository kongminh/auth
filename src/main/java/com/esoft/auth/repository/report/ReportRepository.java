package com.esoft.auth.repository.report;

import com.esoft.auth.entity.report.ReportEntity;
import com.esoft.auth.entity.report.ReportPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportRepository  extends PagingAndSortingRepository<ReportEntity,Long>, JpaRepository<ReportEntity,Long>, JpaSpecificationExecutor<ReportEntity> {

    boolean existsReportEntitiesByTitle(String title);

    @Query(value = "SELECT o FROM ReportEntity o LEFT JOIN ReportPhotoEntity as e ON o.id = e.id WHERE o.userEntity.id=:id")
    Optional<List<ReportEntity>> findReportEntityByUserId(@Param("id") Long id);


}
