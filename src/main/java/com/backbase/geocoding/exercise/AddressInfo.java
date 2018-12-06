package com.backbase.geocoding.exercise;

public class AddressInfo {

    private String formattedAddress;
    private double latitude;
    private double longitude;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    static class Builder {

        private AddressInfo addressInfo;

        Builder() {
            addressInfo = new AddressInfo();
        }

        Builder withFormattedAddress(String formattedAddress) {
            addressInfo.formattedAddress = formattedAddress;
            return this;
        }

        Builder withLatitude(double latitude) {
            addressInfo.latitude = latitude;
            return this;
        }

        Builder withLongitude(double longitude) {
            addressInfo.longitude = longitude;
            return this;
        }

        AddressInfo build() {
            return addressInfo;
        }
    }
}
