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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService extends PrimaryBaseService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportPhotoService reportPhotoService;

    public boolean createReportByUserId(ReportDTO reportDTO, UserEntity userEntity) {

        if (reportRepository.existsReportEntitiesByTitle(reportDTO.getTitle()))
            return false;
        else {
            ReportEntity reportEntity = new ReportEntity();
            reportEntity.setTitle(reportDTO.getTitle());
            reportEntity.setContent(reportDTO.getContent());
            reportEntity.setType(reportDTO.getType());
            reportEntity.setUserEntity(userEntity);
            reportEntity.setReportPhotos(reportDTO.getReports() != null ? reportDTO.getReports()
                    .stream()
                    .map(it -> {
                        ReportPhotoEntity report = new ReportPhotoEntity();
                        report.setId(it.getId());
                        report.setPhotoName(it.getPhotoName());
                        return report;
                    }).collect(Collectors.toList()) : null);

            reportRepository.save(reportEntity);

            return true;
        }
    }

    public List<ReportDTO> getListReportOfUser(Long reportId, PageableRequest pageableRequest) {
        Optional<List<ReportEntity>> reportEntities = reportRepository.findReportEntityByUserId(reportId);

        return reportEntities.map(entities -> entities
                .stream()
                .map(ReportDTO::new)
                .collect(Collectors.toList()))
                .orElse(null);

    }

}
