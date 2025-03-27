package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.name, SUM(sa.amount)) FROM Seller obj JOIN obj.sales sa " +
            "WHERE (:name = '' OR UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))) " +
            "AND sa.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.name")
    List<SummaryDTO> searchSummary(LocalDate maxDate, LocalDate minDate, String name);

    @Query("SELECT new com.devsuperior.dsmeta.dto.ReportDTO(sa.id, sa.date, sa.amount, obj.name) FROM Seller obj JOIN obj.sales sa " +
            "WHERE (:name = '' OR UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))) " +
            "AND sa.date BETWEEN :minDate AND :maxDate ")
    Page<ReportDTO> searchReport(Pageable pageable, LocalDate maxDate, LocalDate minDate, String name);
}
