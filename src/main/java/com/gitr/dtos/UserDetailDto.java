package com.gitr.dtos;

import com.gitr.entities.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

// Dto(data access object) copy of entities without mapping annotations that connects
// entities to the database
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String street1;
    private String street2;
    private String zipCode;
    private String city;
    private String state;
    private String country;
    private String insurance;
    private String type;
    private String additionalDetails;
    private String createdDate;
    private String createdTime;
    private String modifiedDate;
    private String modifiedTime;
    private String isUnauthorized;
    private Set<NoteDto> noteDtoSet = new HashSet<>();

    public UserDetailDto(UserDetail userDetails){
        if (userDetails.getId() != null){
            this.id = userDetails.getId();
        }
        if (userDetails.getFirstName() != null){
            this.firstName = userDetails.getFirstName();
        }
        if (userDetails.getLastName() != null){
            this.lastName = userDetails.getLastName();
        }
        if (userDetails.getAddress() != null){
            this.address = userDetails.getAddress();
        }
        if (userDetails.getStreet1() != null){
            this.street1 = userDetails.getStreet1();
        }
        if (userDetails.getStreet2() != null){
            this.street2 = userDetails.getStreet2();
        }
        if (userDetails.getZipCode() != null){
            this.zipCode = userDetails.getZipCode();
        }
        if (userDetails.getCity() != null){
            this.city = userDetails.getCity();
        }
        if (userDetails.getState() != null){
            this.state = userDetails.getState();
        }
        if (userDetails.getCountry() != null){
            this.country = userDetails.getCountry();
        }
        if (userDetails.getInsurance() != null){
            this.insurance = userDetails.getInsurance();
        }
        if (userDetails.getType() != null){
            this.type = userDetails.getType();
        }
        if (userDetails.getAdditionalDetails() != null){
            this.additionalDetails = userDetails.getAdditionalDetails();
        }
        if (userDetails.getCreatedDate() != null){
            this.createdDate = userDetails.getCreatedDate();
        }
        if (userDetails.getCreatedTime() != null){
            this.createdTime = userDetails.getCreatedTime();
        }
        if (userDetails.getModifiedDate() != null){
            this.modifiedDate = userDetails.getModifiedDate();
        }
        if (userDetails.getModifiedTime() != null){
            this.modifiedTime = userDetails.getModifiedTime();
        }
        if (userDetails.getIsUnauthorized() != null){
            this.isUnauthorized = userDetails.getIsUnauthorized();
        }
    }
}
