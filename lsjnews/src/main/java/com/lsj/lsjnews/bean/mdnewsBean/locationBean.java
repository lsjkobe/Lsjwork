package com.lsj.lsjnews.bean.mdnewsBean;

/**
 * Created by lsj on 2016/3/22.
 */
public class locationBean extends baseBean{
    private String status;
    private Result result;
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }

    public static class Result {
        private Location location;

        private String formatted_address;

        private String business;

        private AddressComponent addressComponent;

        private int cityCode;

        public void setLocation(Location location){
            this.location = location;
        }
        public Location getLocation(){
            return this.location;
        }
        public void setFormatted_address(String formatted_address){
            this.formatted_address = formatted_address;
        }
        public String getFormatted_address(){
            return this.formatted_address;
        }
        public void setBusiness(String business){
            this.business = business;
        }
        public String getBusiness(){
            return this.business;
        }
        public void setAddressComponent(AddressComponent addressComponent){
            this.addressComponent = addressComponent;
        }
        public AddressComponent getAddressComponent(){
            return this.addressComponent;
        }
        public void setCityCode(int cityCode){
            this.cityCode = cityCode;
        }
        public int getCityCode(){
            return this.cityCode;
        }

    }
    public static class AddressComponent {
        private String city;

        private String direction;

        private String distance;

        private String district;

        private String province;

        private String street;

        private String street_number;

        public void setCity(String city){
            this.city = city;
        }
        public String getCity(){
            return this.city;
        }
        public void setDirection(String direction){
            this.direction = direction;
        }
        public String getDirection(){
            return this.direction;
        }
        public void setDistance(String distance){
            this.distance = distance;
        }
        public String getDistance(){
            return this.distance;
        }
        public void setDistrict(String district){
            this.district = district;
        }
        public String getDistrict(){
            return this.district;
        }
        public void setProvince(String province){
            this.province = province;
        }
        public String getProvince(){
            return this.province;
        }
        public void setStreet(String street){
            this.street = street;
        }
        public String getStreet(){
            return this.street;
        }
        public void setStreet_number(String street_number){
            this.street_number = street_number;
        }
        public String getStreet_number(){
            return this.street_number;
        }

    }
    public static class Location {
        private double lng;

        private double lat;

        public void setLng(double lng){
            this.lng = lng;
        }
        public double getLng(){
            return this.lng;
        }
        public void setLat(double lat){
            this.lat = lat;
        }
        public double getLat(){
            return this.lat;
        }

    }
}
