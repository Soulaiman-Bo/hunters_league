package com.javangers.hunters_league.web.vm;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
public class MemberResultsFilterDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Min(value = 0, message = "Page number cannot be negative")
    @Builder.Default
    private Integer page = 0;

    @Min(value = 1, message = "Page size must be greater than 0")
    @Max(value = 100, message = "Page size must not exceed 100")
    @Builder.Default
    private Integer size = 10;
}