package com.helloIftekhar.springJwt.DTO;

import lombok.Data;

@Data
public class DonationStatisticsDTO {
    private Double totalAmount;
    private Double percentageIncrease;

    public DonationStatisticsDTO() {
    }

    public DonationStatisticsDTO(Double totalAmount, Double percentageIncrease) {
        this.totalAmount = totalAmount;
        this.percentageIncrease = percentageIncrease;
    }
}
