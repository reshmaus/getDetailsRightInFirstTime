package com.gitr.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class ProviderGuestDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//it's a primary key and it's Id gets auto incremented
    private Long id;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    //This will lazily retrive data on request,cascade- will delete the main table and also related tables of it
//    @ManyToMany(mappedBy = "providerGuestDetails", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JsonManagedReference //data is saved in jason format||json object created for this note
//    private Set<Note> noteSet = new HashSet<>();

    //constructor using UserDto
    public ProviderGuestDetails(UserDto userDto){
        if (userDto.getUserName() != null){
            this.userName = userDto.getUserName();
        }
        if (userDto.getPassword() != null){
            this.password = userDto.getPassword();
        }
        if (userDto.getEmail() != null){
            this.email = userDto.getEmail();
        }
        if (userDto.getPhoneNumber() != null){
            this.phoneNumber = userDto.getPhoneNumber();
        }
    }
}
