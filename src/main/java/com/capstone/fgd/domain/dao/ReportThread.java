package com.capstone.fgd.domain.dao;

import com.capstone.fgd.domain.Enum.ReportType;
import com.capstone.fgd.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "m_report_thread")
public class ReportThread extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Id_Thread")
    private Threads thread;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    private Users user;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "Report_Type")
    private ReportType reportType;
}
