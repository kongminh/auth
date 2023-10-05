package com.esoft.auth.model.report;

import com.esoft.auth.entity.report.ReportEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ReportDTO {

    private int id;

    @Schema()
    private String title;

    @Schema()
    private String content;

    @Schema()
    private Integer type;

    @Schema()
    private List<ReportPhotoDTO> reports;

    public ReportDTO(ReportEntity reportEntity) {
        this.id = reportEntity.getId();
        this.title = reportEntity.getTitle();
        this.content = reportEntity.getContent();
        this.type = reportEntity.getType();
        this.reports = reportEntity.getReportPhotos() != null &&
                !reportEntity.getReportPhotos().isEmpty() ?
                reportEntity.getReportPhotos().stream()
                        .map(it -> new ReportPhotoDTO(it.getId(), it.getPhotoName()))
                        .collect(Collectors.toList()) : null;
    }
}
