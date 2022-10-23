package com.gitr.controllers;

import com.gitr.dtos.NoteDto;
import com.gitr.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//  need a controller method to show the initial HTML form,will handle all request
@RestController
// needs this to create a rest notes Api path as base url to access
@RequestMapping("api/v1/notes")
public class NoteController {
     @Autowired
    private NoteService noteService;

    // This is rest Api for getting user all notes by user id
     @GetMapping("/user/{userId}")
    public List<NoteDto> getNotesByUser(@PathVariable Long userId){
         return noteService.getAllNotesByUserId(userId);
     }

    // This is rest Api for adding/creating notes for given user id
     @PostMapping("/user/{userId}")
    public void addUserNote(@RequestBody NoteDto noteDto,@PathVariable Long userId){
         noteService.addUserNote(noteDto, userId);
     }

    // This is rest Api for getting provider all notes by user id
    @GetMapping("/provider/{providerId}")
    public List<NoteDto> getNotesByProvider(@PathVariable Long providerId){
        return noteService.getAllNotesByProviderId(providerId);
    }

    // This is rest Api for adding/creating notes for given user id
    @PostMapping("/provider/{providerId}")
    public void addProviderNote(@RequestBody NoteDto noteDto,@PathVariable Long providerId){
        noteService.addProviderNote(noteDto, providerId);
    }

    // This is rest Api for delete the specific note by note id
    @DeleteMapping("/{noteId}")
    public void deleteNoteById(@PathVariable Long noteId){
        noteService.deleteNoteById(noteId);
    }

    // This is rest Api for updating the note
    @PutMapping
    public void updateNote(@RequestBody NoteDto noteDto){
        noteService.updateNoteById(noteDto);
    }

    // This is rest Api for getting notes by note id
    @GetMapping("/{noteId}")
    public Optional<NoteDto> getNoteById(@PathVariable Long noteId){
        return noteService.getNoteById(noteId);
    }

}
