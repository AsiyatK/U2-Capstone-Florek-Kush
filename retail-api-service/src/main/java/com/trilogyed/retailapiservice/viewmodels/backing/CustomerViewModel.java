package com.trilogyed.retailapiservice.viewmodels.backing;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CustomerViewModel {

    private int customerId;
    @NotBlank(message = "Please enter value for First Name")
    @Size(max = 50, min = 1, message = "Size must be between 1 and 50 characters in length.")
    private String firstName;
    @NotBlank(message = "Please enter value for Last Name")
    @Size(max = 50, min = 1, message = "Size must be between 1 and 50 characters in length.")
    private String lastName;
    @NotBlank(message = "Please enter value for Street")
    @Size(max = 50, min = 1, message = "Size must be between 1 and 50 characters in length.")
    private String street;
    @NotBlank(message = "Please enter value for City")
    @Size(max = 50, min = 1, message = "Size must be between 1 and 50 characters in length.")
    private String city;
    @NotBlank(message = "Please enter value for Zip")
    @Size(max = 10, min = 1, message = "Size must be between 1 and 10 characters in length.")
    private String zip;
    @NotBlank(message = "Please enter value for Email")
    @Size(max = 75, min = 1, message = "Size must be between 1 and 75 characters in length.")
    private String email;
    @NotBlank(message = "Please enter value for Phone")
    @Size(max = 20, min = 1, message = "Size must be between 1 and 50 characters in length.")
    private String phone;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerViewModel that = (CustomerViewModel) o;
        return getCustomerId() == that.getCustomerId() &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getStreet(), that.getStreet()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getZip(), that.getZip()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhone(), that.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getFirstName(), getLastName(), getStreet(), getCity(), getZip(), getEmail(), getPhone());
    }
}
