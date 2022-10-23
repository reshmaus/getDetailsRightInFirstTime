package com.gitr.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gitr.dtos.ProviderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity//the persistence objects stores as a record in the database,@Entity tells spring that this class is being mapped to a data source
@Table(name = "Provider")//This is where you can set what table you want these objects to be mapped to
@Data//generates getter and setters by lambok
@AllArgsConstructor// we are writing these instead of creating constructer as lambook is creating for us.
@NoArgsConstructor
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//it's a primary key and it's Id gets auto incremented
    private Long id;

    @Column(unique = true)
    private String providerName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column(unique = true)
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

    //This will lazily retrive data on request,cascade- will delete the main table and also related tables of it
    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference //data is saved in jason format||json object created for this note
    private Set<Note> noteSet = new HashSet<>();

    //constructor using ProviderDto
    public Provider(ProviderDto providerDto){
        if (providerDto.getProviderName() != null){
            this.providerName = providerDto.getProviderName();
        }
        if (providerDto.getPassword() != null){
            this.password = providerDto.getPassword();
        }
        if (providerDto.getEmail() != null){
            this.email = providerDto.getEmail();
        }
        if (providerDto.getPhoneNumber() != null){
            this.phoneNumber = providerDto.getPhoneNumber();
        }
        if (providerDto.getStreet1() != null){
            this.street1 = providerDto.getStreet1();
        }
        if (providerDto.getStreet2() != null){
            this.street2 = providerDto.getStreet2();
        }
        if (providerDto.getZipCode() != null){
            this.zipCode = providerDto.getZipCode();
        }
        if (providerDto.getCity() != null){
            this.city = providerDto.getCity();
        }
        if (providerDto.getState() != null){
            this.state = providerDto.getState();
        }
        if (providerDto.getCountry() != null){
            this.country = providerDto.getCountry();
        }
    }
}
