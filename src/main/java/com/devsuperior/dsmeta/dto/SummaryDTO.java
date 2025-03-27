package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;

public class SummaryDTO {

    private String sellerName;
    private Double total;

    public SummaryDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SummaryDTO(Seller seller) {
        this.sellerName = seller.getName();
        Double total = 0.0;
        for (Sale sale : seller.getSales()) {
            total += sale.getAmount();
        }
        this.total = total;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
