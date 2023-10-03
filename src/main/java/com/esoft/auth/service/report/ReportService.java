package com.esoft.auth.service.report;

import com.esoft.auth.entity.report.ReportEntity;
import com.esoft.auth.entity.report.ReportPhotoEntity;
import com.esoft.auth.model.report.ReportDTO;
import com.esoft.auth.repository.report.ReportRepository;
import com.esoft.auth.service.PrimaryBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ReportService extends PrimaryBaseService {

    @Autowired
    private ReportRepository reportRepository;

    public boolean createReportByUserId(ReportDTO reportDTO) {

        if (reportRepository.existsReportEntitiesByTitle(reportDTO.getTitle()))
            return false;
        else {
            ReportEntity reportEntity = new ReportEntity();
            reportEntity.setTitle(reportDTO.getTitle());
            reportEntity.setContent(reportDTO.getContent());
            reportEntity.setType(reportDTO.getType());
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

}
