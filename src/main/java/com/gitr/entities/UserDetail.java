package com.gitr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gitr.dtos.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity//the persistence objects stores as a record in the database,@Entity tells spring that this class is being mapped to a data source
@Table(name = "UserDetails")//This is where you can set what table you want these objects to be mapped to
@Data//generates getter and setters by lambok
@AllArgsConstructor// we are writing these instead of creating constructer as lambook is creating for us.
@NoArgsConstructor
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//it's a primary key and it's Id gets auto incremented
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String address;

    @Column
    private String street1;

    @Column
    private String street2;

    @Column
    private String zipCode;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    private String insurance;

    @Column
    private String type;

    @Column
    private String additionalDetails;

    @Column
    private String createdDate;

    @Column
    private String createdTime;

    @Column
    private String modifiedDate;

    @Column
    private String modifiedTime;

    @Column
    private String isUnauthorized;

    @ManyToOne
    @JsonBackReference
    private User user;

    //This will lazily retrive data on request,cascade- will delete the main table and also related tables of it
//    @OneToMany(mappedBy = "userDetails", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JsonManagedReference //data is saved in jason format||json object created for this note
//    private Set<Note> noteSet = new HashSet<>();

    //constructor using UserDetailsDto
    public UserDetail(UserDetailDto userDetailsDto){
        if (userDetailsDto.getFirstName() != null){
            this.firstName = userDetailsDto.getFirstName();
        }
        if (userDetailsDto.getLastName() != null){
            this.lastName = userDetailsDto.getLastName();
        }
        if (userDetailsDto.getAddress() != null){
            this.address = userDetailsDto.getAddress();
        }
        if (userDetailsDto.getStreet1() != null){
            this.street1 = userDetailsDto.getStreet1();
        }
        if (userDetailsDto.getStreet2() != null){
            this.street2 = userDetailsDto.getStreet2();
        }
        if (userDetailsDto.getZipCode() != null){
            this.zipCode = userDetailsDto.getZipCode();
        }
        if (userDetailsDto.getCity() != null){
            this.city = userDetailsDto.getCity();
        }
        if (userDetailsDto.getState() != null){
            this.state = userDetailsDto.getState();
        }
        if (userDetailsDto.getCountry() != null){
            this.country = userDetailsDto.getCountry();
        }
        if (userDetailsDto.getInsurance() != null){
            this.insurance = userDetailsDto.getInsurance();
        }
        if (userDetailsDto.getType() != null){
            this.type = userDetailsDto.getType();
        }
        if (userDetailsDto.getAdditionalDetails() != null){
            this.additionalDetails = userDetailsDto.getAdditionalDetails();
        }
        if (userDetailsDto.getCreatedDate() != null){
            this.createdDate = userDetailsDto.getCreatedDate();
        }
        if (userDetailsDto.getCreatedTime() != null){
            this.createdTime = userDetailsDto.getCreatedTime();
        }
        if (userDetailsDto.getModifiedDate() != null){
            this.modifiedDate = userDetailsDto.getModifiedDate();
        }
        if (userDetailsDto.getModifiedTime() != null){
            this.modifiedTime = userDetailsDto.getModifiedTime();
        }
        if (userDetailsDto.getIsUnauthorized() != null){
            this.isUnauthorized = userDetailsDto.getIsUnauthorized();
        }
    }
}
