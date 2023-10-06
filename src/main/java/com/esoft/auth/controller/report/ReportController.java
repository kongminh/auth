package com.esoft.auth.controller.report;

import com.esoft.auth.component.RabbitMQConsumer;
import com.esoft.auth.component.RabbitMQProducer;
import com.esoft.auth.entity.PageableRequest;
import com.esoft.auth.model.report.ReportDTO;
import com.esoft.auth.security.model.LoginUserDetails;
import com.esoft.auth.service.report.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@SecurityRequirement(name = "esoft-api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @PostMapping("/create")
    public boolean createReport(@RequestBody ReportDTO reportDTO,
                                @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        return reportService.createReportByUserId(reportDTO, loginUserDetails.getUserInfo());
    }

    @PostMapping("/{reportId}")
    public List<ReportDTO> getAllListReportOfUser(@PathVariable String reportId,
                                                  @RequestBody PageableRequest pageable) {
        rabbitMQProducer.sendMessage("heeeeekkkkkkkk");

        return reportService.getListReportOfUser(Long.parseLong(reportId), pageable);
    }
}
