/**
 * Copyright (c) 2020, CargoLink Vietnam Interconnection Joint Stock Company.<br>
 * All rights reserved.
 */
package com.esoft.auth.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableRequest {
  private Integer page = 0;
  private Integer rowsPerPage = 10;
  private String sortBy;
  private boolean descending;
}
