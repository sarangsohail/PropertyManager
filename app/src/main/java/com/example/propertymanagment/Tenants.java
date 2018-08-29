package com.example.propertymanagment;

    public class Tenants {

        public String date;
        public String mStatus;

        public Tenants(){

        }

        public Tenants(String date, String mStatus) {
            this.date = date;
            this.mStatus = mStatus;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getmStatus() {
            return mStatus;
        }

        public void setmStatus(String mStatus) {
            this.mStatus = mStatus;
        }


    }
