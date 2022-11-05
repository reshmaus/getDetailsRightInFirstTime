package com.gitr.services;

import com.gitr.dtos.GuestDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface GuestService {
    @Transactional
    Optional<GuestDto> addProviderGuest(GuestDto guestDto, Long providerId);

    @Transactional
    List<GuestDto> getAllGuestByProviderId(Long providerId);

    @Transactional
    Optional<GuestDto> addGuest(GuestDto guestDto);

    @Transactional
    void deleteGuest(Long guestId);

    @Transactional
    Optional<GuestDto> updateGuest(GuestDto guestDto);

    List<GuestDto> getAllGuest();

    Optional<GuestDto> getGuestById(Long guestId);
}
