package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;





@Entity
@Table(name="refunds")
public class Refund extends BaseEntity {

        @Column(name = "refund_id", nullable = false, length = 20)
        private Long refund_id;

        @Column(name = "refund_status", nullable = false, length = 20)
        private String refund_status;

        @Column(name = "refund_amount", nullable = false, length = 50)
        private double refund_amount;

        @Column(name = "stripe_refund_id", nullable = false, unique = true, length = 50)
        private Long stripe_refund_id;



        public Long getrefund_id() {
            return refund_id;
        }

        public void setrefund_id(Long refund_id) {
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


