package com.gitr.controllers;

import com.gitr.dtos.GuestDto;
import com.gitr.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// needs this to create a rest notes Api path as base url to access
@RequestMapping("api/v1/guest")
public class GuestController {
    @Autowired
    private GuestService guestService;

    // This is rest Api for getting user all guest
    @GetMapping("/getAll")
    public List<GuestDto> getAllGuest() {
        return guestService.getAllGuest();
    }

    @GetMapping("/getAllGuestByProviderId/{providerId}")
    public List<GuestDto> getAllGuestByProviderId(@PathVariable Long providerId) {
        return guestService.getAllGuestByProviderId(providerId);
    }

    // This is rest Api for adding/creating guest
    @PostMapping("/createGuestByProviderId/{providerId}")
    public Optional<GuestDto> addProviderGuest(@RequestBody GuestDto guestDto, @PathVariable Long providerId) {
        return guestService.addProviderGuest(guestDto, providerId);
    }

    // This is rest Api for adding/creating guest
    @PostMapping("/create")
    public Optional<GuestDto> addGuest(@RequestBody GuestDto guestDto) {
        return guestService.addGuest(guestDto);
    }

    // This is rest Api for delete the specific guest by guest id
    @DeleteMapping("/delete/{guestId}")
    public void deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
    }

    // This is rest Api for updating the guest
    @PutMapping("/update")
    public Optional<GuestDto> updateGuest(@RequestBody GuestDto guestDto) {
        return guestService.updateGuest(guestDto);
    }

    // This is rest Api for getting guest by guest id
    @GetMapping("/getById/{guestId}")
    public Optional<GuestDto> getGuestById(@PathVariable Long guestId) {
        return guestService.getGuestById(guestId);
    }
}
