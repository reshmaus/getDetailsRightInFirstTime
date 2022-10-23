//This is data object, we use this to transfer data from one object to other

package com.gitr.dtos;

import com.gitr.entities.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

// Dto(data access object) copy of entities without mapping annotations that connects
// entities to the database
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ProviderDto implements Serializable {
        private Long id;
        private String providerName;
        private String password;
        private String email;
        private String phoneNumber;
        private String street1;
        private String street2;
        private String zipCode;
        private String city;
        private String state;
        private String country;

        private Set<NoteDto> noteDtoSet = new HashSet<>();

        public ProviderDto(Provider provider) {
            if (provider.getId() != null){
                this.id = provider.getId();
            }
            if (provider.getProviderName() != null){
                this.providerName = provider.getProviderName();
            }
            if (provider.getPassword() != null){
                this.password = provider.getPassword();
            }
            if (provider.getEmail() != null){
                this.email = provider.getEmail();
            }
            if (provider.getPhoneNumber() != null){
                this.phoneNumber = provider.getPhoneNumber();
            }
            if (provider.getStreet1() != null){
                this.street1 = provider.getStreet1();
            }
            if (provider.getStreet2() != null){
                this.street2 = provider.getStreet2();
            }
            if (provider.getZipCode() != null){
                this.zipCode = provider.getZipCode();
            }
            if (provider.getCity() != null){
                this.city = provider.getCity();
            }
            if (provider.getState() != null){
                this.state = provider.getState();
            }
            if (provider.getCountry() != null){
                this.country = provider.getCountry();
            }
        }
}
