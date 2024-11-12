package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RefundDTO {



    private Long refund_id;
    private String refund_status;
    private double refund_amount;
    private Long stripe_refund_id;



    public RefundDTO(Long refund_id, String refund_status, double refund_amount, Long stripe_refund_id) {
        this.refund_id = refund_id;
        this.refund_status = refund_status;
        this.refund_amount = refund_amount;
        this.stripe_refund_id = stripe_refund_id;

    }

    public Long getrefund_id() {
        return refund_id;
    }

    public void setrefund_id(Long id) {
        this.refund_id = refund_id;
    }

    public String getrefund_status() {
        return refund_status;
    }

    public void setrefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public double getrefund_amount() {
        return refund_amount;
    }

    public void setrefund_amount(double refund_amount) {
        this.refund_amount = refund_amount;
    }

    public Long getstripe_refund_id() {
        return stripe_refund_id;
    }

    public void setstripe_refund_id(Long stripe_refund_id) {
        this.stripe_refund_id = stripe_refund_id;
    }


}


