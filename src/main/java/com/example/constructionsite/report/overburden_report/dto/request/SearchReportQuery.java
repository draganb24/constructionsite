package com.example.constructionsite.report.overburden_report.dto.request;

import java.time.LocalDate;

public record SearchReportQuery(
    LocalDate startDate,
    LocalDate endDate,
    Long machineId,
    Long workerId
) {
}
