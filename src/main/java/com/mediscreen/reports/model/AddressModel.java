package com.mediscreen.reports.model;

public class AddressModel {

    private Integer id;

    private PatientModel patientAddress;

    private String street;

    private String city;

    private String postcode;

    private String district;

    private String state;

    private String country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientModel getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(PatientModel patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}