package com.esoft.auth.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.esoft.auth.config.DataSourcePrimaryConfig;


@Transactional(
    propagation = Propagation.REQUIRED,
    rollbackFor = Exception.class,
    transactionManager = DataSourcePrimaryConfig.TRANSACTION_MANAGER)
public class PrimaryBaseService {
}