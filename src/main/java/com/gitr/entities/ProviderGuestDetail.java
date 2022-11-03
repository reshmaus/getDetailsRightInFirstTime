package com.gitr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gitr.dtos.ProviderGuestDetailDto;
import com.gitr.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//the persistence objects stores as a record in the database,@Entity tells spring that this class is being mapped to a data source
@Table(name = "ProviderGuestDetails")//This is where you can set what table you want these objects to be mapped to
@Data//generates getter and setters by lambok
@AllArgsConstructor// we are writing these instead of creating constructer as lambook is creating for us.
@NoArgsConstructor
public class ProviderGuestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//it's a primary key and it's Id gets auto incremented
    private Long id;

//    @ManyToMany
//    @JsonBackReference
//    private Set<Guest> guestSet = new HashSet<>();
//
//    @ManyToMany
//    @JsonBackReference
//    private Set<Provider> providerSet = new HashSet<>();

    @ManyToMany(mappedBy = "providerGuestDetail", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference //data is saved in jason format||json object created for this note
    private Set<Guest> guest = new HashSet<>();

    @ManyToMany(mappedBy = "providerGuestDetail", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference //data is saved in jason format||json object created for this note
    private Set<UserDetail> userDetail = new HashSet<>();

    @ManyToMany(mappedBy = "providerGuestDetail", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference //data is saved in jason format||json object created for this note
    private Set<Provider> provider = new HashSet<>();

    //constructor using UserDto
    public ProviderGuestDetail(ProviderGuestDetailDto providerGuestDetailDto){
        if (providerGuestDetailDto.getId() != null){
            this.id = providerGuestDetailDto.getId();
        }
    }
}
