package com.gitr.dtos;

import com.gitr.entities.Guest;
import com.gitr.entities.UserDetails;
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
public class GuestDto implements Serializable {
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

//    private Set<NoteDto> noteDtoSet = new HashSet<>();

    public GuestDto(Guest guest){
        if (guest.getId() != null){
            this.id = guest.getId();
        }
        if (guest.getFirstName() != null){
            this.firstName = guest.getFirstName();
        }
        if (guest.getLastName() != null){
            this.lastName = guest.getLastName();
        }
        if (guest.getAddress() != null){
            this.address = guest.getAddress();
        }
        if (guest.getStreet1() != null){
            this.street1 = guest.getStreet1();
        }
        if (guest.getStreet2() != null){
            this.street2 = guest.getStreet2();
        }
        if (guest.getZipCode() != null){
            this.zipCode = guest.getZipCode();
        }
        if (guest.getCity() != null){
            this.city = guest.getCity();
        }
        if (guest.getState() != null){
            this.state = guest.getState();
        }
        if (guest.getCountry() != null){
            this.country = guest.getCountry();
        }
        if (guest.getInsurance() != null){
            this.insurance = guest.getInsurance();
        }
        if (guest.getType() != null){
            this.type = guest.getType();
        }
        if (guest.getAdditionalDetails() != null){
            this.additionalDetails = guest.getAdditionalDetails();
        }
        if (guest.getCreatedDate() != null){
            this.createdDate = guest.getCreatedDate();
        }
        if (guest.getCreatedTime() != null){
            this.createdTime = guest.getCreatedTime();
        }

    }
}
