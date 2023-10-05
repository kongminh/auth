package com.esoft.auth.service.report;

import com.esoft.auth.entity.report.ReportPhotoEntity;
import com.esoft.auth.repository.report.ReportPhotoRepository;
import com.esoft.auth.service.PrimaryBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportPhotoService extends PrimaryBaseService {

    @Autowired
    ReportPhotoRepository reportPhotoRepository;


}
