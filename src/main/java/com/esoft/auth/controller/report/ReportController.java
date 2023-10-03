package com.esoft.auth.controller.report;

import com.esoft.auth.entity.user.UserEntity;
import com.esoft.auth.model.report.ReportDTO;
import com.esoft.auth.service.report.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;

@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/create")
    public boolean createReport(@RequestBody ReportDTO reportDTO,
                                @AuthenticationPrincipal UserEntity userEntity) {
        System.out.println(userEntity.getUsername());
        return reportService.createReportByUserId(reportDTO);
    }
}
