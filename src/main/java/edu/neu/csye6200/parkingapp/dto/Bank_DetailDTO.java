package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class Bank_DetailDTO {



        private Long bank_detail_id;
        @NotBlank(message = "Bank name is required")
        @Size(min = 2, max = 20, message = "Bank name must be between 2 and 20 characters")
        private String bank_name;
        @NotBlank(message = "Account type is required")
        @Size(min = 2, max = 20, message = "Account type must be between 2 and 20 characters")
        private String account_type;
        @NotBlank(message = "Account number is required")
        @Size(min = 2, max = 20, message = "Account number must be between 2 and 20 characters")
        private Long account_number;
        @NotBlank(message = "Routing number is required")
        @Size(min = 2, max = 20, message = "Routing number must be between 2 and 20 characters")
        private Long routing_number;



        public Bank_DetailDTO(Long bank_detail_id, String bank_name, String account_type, Long account_number, Long routing_number) {
            this.bank_detail_id = bank_detail_id;
            this.bank_name = bank_name;
            this.account_type = account_type;
            this.account_number = account_number;
            this.routing_number = routing_number;

        }

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

        public Long getaccount_number() {
            return account_number;
        }

        public void setaccount_number(Long account_number) {
            this.account_number = account_number;
        }

        public Long getrouting_number() {
        return routing_number;
    }

        public void setrouting_number(Long routing_number) {
        this.routing_number = routing_number;
    }



}



