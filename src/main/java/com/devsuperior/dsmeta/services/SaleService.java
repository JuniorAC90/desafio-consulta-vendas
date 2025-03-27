package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerTotalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SellerTotalDTO> findSummary(String minDate, String maxDate, String name) {
		LocalDate min;
		LocalDate max;

		if (maxDate != null) {
			max = LocalDate.parse(maxDate);
		} else {
			max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}

		if (minDate != null) {
			min = LocalDate.parse(minDate);
		} else {
			min = max.minusYears(1L);
		}

		return repository.searchSellerTotalSales(max, min, name);
	}
}
