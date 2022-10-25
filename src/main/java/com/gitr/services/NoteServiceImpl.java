package com.gitr.services;


import com.gitr.dtos.NoteDto;
import com.gitr.entities.Note;
import com.gitr.entities.Provider;
import com.gitr.entities.User;
import com.gitr.repositories.NoteRepository;
import com.gitr.repositories.ProviderRepository;
import com.gitr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//This is where actual Business logic is for Note scope, the fetch and push to DB happens

// These services are used to execute CRUD SQL call using transactional and methods have been Implemented here
@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public void addUserNote(NoteDto noteDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Note note = new Note(noteDto);
        userOptional.ifPresent(note::setUser);
        noteRepository.saveAndFlush(note);
    }

    @Override
    public void addProviderNote(NoteDto noteDto, Long providerId) {
        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        Note note = new Note(noteDto);
        providerOptional.ifPresent(note::setProvider);
        noteRepository.saveAndFlush(note);
    }

    @Override
    // This is where note will be deleted by note id from DB
    public void deleteNoteById(Long noteId) {
      Optional<Note> noteOptional = noteRepository.findById(noteId);
      noteOptional.ifPresent(note -> noteRepository.delete(note));
    }

    @Override
    // This is where note will be updated by note id to DB
    public void updateNoteById(NoteDto noteDto) {
     Optional<Note> noteOptional = noteRepository.findById(noteDto.getId() );
     noteOptional.ifPresent(note -> {
         note.setBody(noteDto.getBody());
         noteRepository.saveAndFlush(note);
     });
    }

    @Override
    // This is where all note fetched from DB by user id
    public List<NoteDto> getAllNotesByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            List<Note> noteList = noteRepository.findAllByUserEquals(userOptional.get());
            return noteList.stream().map(note -> new NoteDto(note)).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    @Override
    public List<NoteDto> getAllNotesByProviderId(Long providerId) {
        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        if(providerOptional.isPresent()){
            List<Note> noteList = noteRepository.findAllByProviderEquals(providerOptional.get());
            return noteList.stream().map(note -> new NoteDto(note)).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    @Override
    // This is where note details is fetched from DB by note id
    public Optional<NoteDto> getNoteById(Long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if(noteOptional.isPresent()){
            return Optional.of(new NoteDto(noteOptional.get()));
        }
        return Optional.empty();
    }

}


