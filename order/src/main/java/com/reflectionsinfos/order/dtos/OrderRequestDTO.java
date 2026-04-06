package com.reflectionsinfos.order.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {
    @NotBlank(message = "Customer name is mandatory")
    private String customerName;

    @NotNull(message = "Amount is mandatory")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }
}
