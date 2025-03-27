package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SellerTotalDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SellerTotalDTO(obj.name, SUM(sa.amount)) FROM Seller obj JOIN obj.sales sa " +
            "WHERE (:name = '' OR UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))) " +
            "AND sa.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.name")
    List<SellerTotalDTO> searchSellerTotalSales(LocalDate maxDate, LocalDate minDate, String name);
}
