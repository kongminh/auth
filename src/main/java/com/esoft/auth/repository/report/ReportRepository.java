package com.esoft.auth.repository.report;

import com.esoft.auth.entity.report.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ReportRepository  extends JpaRepository<ReportEntity,Long>, JpaSpecificationExecutor<ReportEntity> {

    boolean existsReportEntitiesByTitle(String title);

    Optional<List<ReportEntity>> findAllById(int id);

}
