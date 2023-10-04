package com.esoft.auth.service.report;

import com.esoft.auth.entity.PageableRequest;
import com.esoft.auth.entity.report.ReportEntity;
import com.esoft.auth.entity.report.ReportPhotoEntity;
import com.esoft.auth.entity.user.UserEntity;
import com.esoft.auth.model.report.ReportDTO;
import com.esoft.auth.repository.report.ReportRepository;
import com.esoft.auth.service.PrimaryBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService extends PrimaryBaseService {

    @Autowired
    private ReportRepository reportRepository;

    public boolean createReportByUserId(ReportDTO reportDTO, UserEntity userEntity) {

        if (reportRepository.existsReportEntitiesByTitle(reportDTO.getTitle()))
            return false;
        else {
            ReportEntity reportEntity = new ReportEntity();
            reportEntity.setTitle(reportDTO.getTitle());
            reportEntity.setContent(reportDTO.getContent());
            reportEntity.setType(reportDTO.getType());
            reportEntity.setUserEntity(userEntity);
            if (reportDTO.getReports() != null)
                reportEntity.setReportPhotos(reportDTO.getReports()
                        .stream()
                        .map(it -> {
                            ReportPhotoEntity report = new ReportPhotoEntity();
                            report.setId(it.getId());
                            report.setPhotoName(it.getPhotoName());
                            return report;
                        }).collect(Collectors.toList()));

            reportRepository.save(reportEntity);

            return true;
        }
    }

    public Page<ReportDTO> getListReportOfUser(int reportId, PageableRequest pageableRequest) {

        Pageable pageable = paginationWith(pageableRequest, "id");

        Page<ReportEntity> reportEntities = reportRepository.findAll(
                Specification.where(
                        (root, query, criteriaBuilder) -> {
                            Predicate predicate = criteriaBuilder.equal(root.get("id"), reportId);
                            return predicate;
                        }
                ),
                pageable
        );
        System.out.println(reportEntities);
        return new PageImpl<>(
                reportEntities
                        .stream()
                        .map(ReportDTO::new)
                        .collect(Collectors.toList()),
                pageable,
                reportEntities.getTotalElements());
    }

}
