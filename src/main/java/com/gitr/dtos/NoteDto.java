//This is data object, we use this to transfer data from one object to other

package com.gitr.dtos;

import com.gitr.entities.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class NoteDto implements Serializable {
        private Long id;
        private String body;
        private UserDto userDto;


        public NoteDto(Note note) {
            if (note.getId() != null){
                this.id = note.getId();
            }
            if (note.getBody() != null){
                this.body = note.getBody();
            }

        }
    }



