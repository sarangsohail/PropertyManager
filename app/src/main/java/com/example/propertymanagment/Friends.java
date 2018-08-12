package com.example.propertymanagment;

    public class Friends {

        //currently, the only value in the db
        public String date;
        public String mStatus;

        public Friends(){

        }

        public Friends(String date) {
            this.date = date;
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
