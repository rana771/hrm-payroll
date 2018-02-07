package com.bracu.hrm.dto;

import com.bracu.hrm.model.EmployeeEducation;
import lombok.Data;

/**
 * Created by HP on 1/23/2018.
 */
@Data
public class EmpEducationDto {
    private EmployeeEducation employeeEducation;
    private Integer employeeId;
    private Integer educationTitleId;
}
