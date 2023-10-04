package com.esoft.auth.service;

import com.esoft.auth.entity.PageableRequest;
import com.esoft.auth.entity.user.UserEntity;
import com.esoft.auth.exceptions.AppValidationException;
import com.esoft.auth.model.UserDTO;
import com.esoft.auth.security.model.LoginUserDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.esoft.auth.config.DataSourcePrimaryConfig;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = Exception.class,
        transactionManager = DataSourcePrimaryConfig.TRANSACTION_MANAGER)
public class PrimaryBaseService {

    protected UserEntity getUser() {
        org.springframework.security.core.Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ValidationException("error.unauthorized");
        }
        return ((LoginUserDetails) auth.getPrincipal()).getUserInfo();
    }

    public Pageable createPageRequest(int pageNumber, int recordNumber, List<Sort.Order> orders) {
        return PageRequest.of(pageNumber, recordNumber, Sort.by(orders));
    }

    protected static List<Sort.Order> getOrderBy(
            String sortBy, boolean isDescending, String defaultField, Sort.Direction direction) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(
                new Sort.Order(
                        !(sortBy == null || sortBy.isEmpty())
                                ? (isDescending ? Sort.Direction.DESC : Sort.Direction.ASC)
                                : direction,
                        !(sortBy == null || sortBy.isEmpty()) ? sortBy : defaultField));
        return orders;
    }

    protected <T extends PageableRequest> Pageable paginationWith(T pageable, String defaultField) {
        return createPageRequest(
                pageable.getPage(),
                pageable.getRowsPerPage(),
                getOrderBy(
                        pageable.getSortBy(),
                        pageable.isDescending(),
                        defaultField,
                        Sort.Direction.DESC));
    }
}
