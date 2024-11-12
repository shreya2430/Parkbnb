package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="bank_details")

public class Bank_Detail extends BaseEntity {


        @Column(name = "bank_detail_id", nullable = false, length = 20)
        private Long bank_detail_id;

        @Column(name = "bank_name", nullable = false, length = 20)
        private String bank_name;

        @Column(name = "account_type", nullable = false, length = 50)
        private String account_type;

        @Column(name = "account_number", nullable = false, unique = true, length = 50)
        private Long account_number;

        @Column(name = "routing_number", nullable = false, unique = true, length = 50)
         private Long routing_number;



        public Long getbank_detail_id() {
            return bank_detail_id;
        }

        public void setbank_detail_id(Long bank_detail_id) {
            this.bank_detail_id = bank_detail_id;
        }

        public String getbank_name() {
        return bank_name;
    }

        public void setbank_name(String bank_name) {
        this.bank_name = bank_name;
    }

        public String getaccount_type() {
            return account_type;
        }

        public void setaccount_type(String account_type) {
            this.account_type = account_type;
        }

        public Long getaccount_number() {return account_number;}

        public void setaccount_number(Long account_number) {
        this.account_number = account_number;
    }

        public Long getrouting_number() {return routing_number;}

        public void setrouting_number(Long routing_number) {
        this.routing_number = routing_number;
    }

}



