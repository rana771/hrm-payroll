package com.bracu.hrm.model.settings;

import com.bracu.hrm.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "job_level")
@Data
public class JobLevel extends BaseEntity{

    private String level;

    @Column(name="int_level")
    private Integer intLevel;

    private String note;
}

