package com.example.pkcn.dto.request;

public class UpdateOrderStatusRequest {

    private String status;

    public UpdateOrderStatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}