package com.esoft.auth.repository.report;

import com.esoft.auth.entity.report.ReportPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ReportPhotoRepository extends PagingAndSortingRepository<ReportPhotoEntity, Integer>, JpaRepository<ReportPhotoEntity, Integer>,
        JpaSpecificationExecutor<ReportPhotoEntity> {

    Optional<List<ReportPhotoEntity>> findAllById(int id);
}
