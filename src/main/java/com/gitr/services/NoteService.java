package com.gitr.services;

import com.gitr.dtos.NoteDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//This is the interface for accessing the methods
// These services are used to execute CRUD SQL call using transactional and methods have been defined here
public interface NoteService {
    @Transactional
    void addUserNote(NoteDto noteDto, Long userId);

    @Transactional
    void addProviderNote(NoteDto noteDto, Long providerId);

    @Transactional
    void deleteNoteById(Long noteId);

    @Transactional
    void updateNoteById(NoteDto noteDto);

    List<NoteDto> getAllNotesByUserId(Long userId);

    List<NoteDto> getAllNotesByProviderId(Long providerId);

    Optional<NoteDto> getNoteById(Long noteId);
}
