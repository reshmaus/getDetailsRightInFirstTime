package com.gitr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gitr.dtos.GuestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity//the persistence objects stores as a record in the database,@Entity tells spring that this class is being mapped to a data source
@Table(name = "Guest")//This is where you can set what table you want these objects to be mapped to
@Data//generates getter and setters by lambok
@AllArgsConstructor// we are writing these instead of creating constructer as lambook is creating for us.
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//it's a primary key and it's Id gets auto incremented
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

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

    @ManyToOne
    @JsonBackReference
    private Provider provider;


    //This will lazily retrive data on request,cascade- will delete the main table and also related tables of it
//    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JsonManagedReference //data is saved in jason format||json object created for this note
//    private Set<Note> noteSet = new HashSet<>();

    //constructor using GuestDto
    public Guest(GuestDto guestDto){
        if (guestDto.getFirstName() != null){
            this.firstName = guestDto.getFirstName();
        }
        if (guestDto.getLastName() != null){
            this.lastName = guestDto.getLastName();
        }
        if (guestDto.getStreet1() != null){
            this.street1 = guestDto.getStreet1();
        }
        if (guestDto.getStreet2() != null){
            this.street2 = guestDto.getStreet2();
        }
        if (guestDto.getZipCode() != null){
            this.zipCode = guestDto.getZipCode();
        }
        if (guestDto.getCity() != null){
            this.city = guestDto.getCity();
        }
        if (guestDto.getState() != null){
            this.state = guestDto.getState();
        }
        if (guestDto.getCountry() != null){
            this.country = guestDto.getCountry();
        }
        if (guestDto.getInsurance() != null){
            this.insurance = guestDto.getInsurance();
        }
        if (guestDto.getType() != null){
            this.type = guestDto.getType();
        }
        if (guestDto.getAdditionalDetails() != null){
            this.additionalDetails = guestDto.getAdditionalDetails();
        }
        if (guestDto.getCreatedDate() != null){
            this.createdDate = guestDto.getCreatedDate();
        }
        if (guestDto.getCreatedTime() != null){
            this.createdTime = guestDto.getCreatedTime();
        }

    }
}
