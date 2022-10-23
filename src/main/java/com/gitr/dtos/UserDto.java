//This is data object, we use this to transfer data from one object to other

package com.gitr.dtos;

import com.gitr.entities.User;
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
    public class UserDto implements Serializable {
        private Long id;
        private String userName;
        private String password;
        private String email;
        private String phoneNumber;
        private Set<NoteDto> noteDtoSet = new HashSet<>();

        public UserDto(User user) {
            if (user.getId() != null){
                this.id = user.getId();
            }
            if (user.getUserName() != null){
                this.userName = user.getUserName();
            }
            if (user.getPassword() != null){
                this.password = user.getPassword();
            }
            if (user.getEmail() != null){
                this.email = user.getEmail();
            }
            if (user.getPhoneNumber() != null){
                this.phoneNumber = user.getPhoneNumber();
            }
        }
}
