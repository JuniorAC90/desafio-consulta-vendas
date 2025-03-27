package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public List<SummaryDTO> findSummary(String minDate, String maxDate, String name) {
		LocalDate max = getMaxDate(maxDate);
		LocalDate min = getMinDate(minDate, max);

		return repository.searchSummary(max, min, name);
	}

	public Page<ReportDTO> findReport(Pageable pageable, String minDate, String maxDate, String name) {
		LocalDate max = getMaxDate(maxDate);
		LocalDate min = getMinDate(minDate, max);

		return repository.searchReport(pageable, max, min, name);
	}

	public LocalDate getMaxDate(String date) {
		if (date != null) {
			return LocalDate.parse(date);
		} else {
			return LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
	}

	public LocalDate getMinDate(String date, LocalDate maxDate) {
		if (date != null) {
			return LocalDate.parse(date);
		} else {
			return maxDate.minusYears(1L);
		}
	}
}
