package com.helloIftekhar.springJwt.DTO;

import lombok.Data;

@Data
public class PostStatisticsDTO {
    private Long total;
    private double percentageIncrease;

    public PostStatisticsDTO() {
    }
    public PostStatisticsDTO(Long total, double percentageIncrease) {
        this.total = total;
        this.percentageIncrease = percentageIncrease;
    }
}
