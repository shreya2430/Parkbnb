package edu.neu.csye6200.parkingapp.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "parkinglocation")
public class ParkingLocation extends BaseEntity {

    @Column(name = "street",nullable = false , length = 20)
    private  String street;

    @Column(name = "city",nullable = false , length = 20)
    private  String city;

    @Column(name = "state",nullable = false , length = 20)
    private  String state;

    @Column(name = "postalcode",nullable = false , length = 8)
    private  String postalcode;

    @Column(name = "country",nullable = false , length = 30)
    private  String country;

    @Column(name = "latitude",nullable = false , length = 30)
    private  String latitude;

    @Column(name = "longitude",nullable = false , length = 30)
    private  String longitude;

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalcode() { return postalcode; }
    public void setPostalcode(String postalcode) { this.postalcode = postalcode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }




}
