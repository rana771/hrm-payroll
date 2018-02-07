package com.bracu.hrm.dto;

import lombok.Data;

/**
 * Created by HP on 1/29/2018.
 */
@Data
public class EmpAddressDto {
    private String address;
    private String isActive;
    private Integer id;
    private Integer employeeId;
    private Integer addressTypeId;
    private Integer version;
}
