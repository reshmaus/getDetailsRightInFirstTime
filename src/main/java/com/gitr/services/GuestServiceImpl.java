package com.gitr.services;

import com.gitr.dtos.GuestDto;
import com.gitr.dtos.NoteDto;
import com.gitr.dtos.UserDetailDto;
import com.gitr.entities.*;
import com.gitr.repositories.GuestRepository;
import com.gitr.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//This is where actual Business logic is for Note scope, the fetch and push to DB happens

// These services are used to execute CRUD SQL call using transactional and methods have been Implemented here
@Service
public class GuestServiceImpl implements GuestService{
    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public Optional<GuestDto> addProviderGuest(GuestDto guestDto, Long providerId) {
        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        Guest guest = new Guest(guestDto);
        providerOptional.ifPresent(guest::setProvider);
        guestRepository.save(guest);
        return Optional.of(new GuestDto(guest));
    }

    @Override
    public List<GuestDto> getAllUserDetailByUserId(Long providerId) {
        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        if(providerOptional.isPresent()){
            List<Guest> guestList = guestRepository.findAllByProviderEquals(providerOptional.get());
            return guestList.stream().map(guest -> new GuestDto(guest)).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    @Override
    public Optional<GuestDto> addGuest(GuestDto guestDto) {
        Guest guest = new Guest(guestDto);
        guestRepository.save(guest);
        return Optional.of(new GuestDto(guest));
    }

    @Override
    public void deleteGuest(Long guestId) {
        Optional<Guest> guestOptional = guestRepository.findById(guestId);
        guestOptional.ifPresent(guest -> guestRepository.delete(guest));
    }

    @Override
    public Optional<GuestDto> updateGuest(GuestDto guestDto) {
        Optional<Guest> guestOptional = guestRepository.findById(guestDto.getId());
        guestOptional.ifPresent(guest -> {
            guest.setFirstName(guestDto.getFirstName());
            guest.setLastName(guestDto.getLastName());
            guest.setStreet1(guestDto.getStreet1());
            guest.setStreet2(guestDto.getStreet2());
            guest.setZipCode(guestDto.getZipCode());
            guest.setCity(guestDto.getCity());
            guest.setState(guestDto.getState());
            guest.setCountry(guestDto.getCountry());
            guest.setInsurance(guestDto.getInsurance());
            guest.setType(guestDto.getType());
            guest.setAdditionalDetails(guestDto.getAdditionalDetails());
            guest.setCreatedDate(guestDto.getCreatedDate());
            guest.setCreatedTime(guestDto.getCreatedTime());

            guestRepository.saveAndFlush(guest);
        });

        return Optional.of(new GuestDto(guestOptional.get()));
    }

    @Override
    public List<GuestDto> getAllGuest() {
            List<Guest> guestList = guestRepository.findAll();
            if(!guestList.isEmpty()){
                return guestList.stream().map(guest -> new GuestDto(guest)).collect(Collectors.toList());
            }
        return Collections.emptyList();
    }

    @Override
    public Optional<GuestDto> getGuestById(Long guestId) {
        Optional<Guest> guestOptional = guestRepository.findById(guestId);
        if(guestOptional.isPresent()){
            return Optional.of(new GuestDto(guestOptional.get()));
        }
        return Optional.empty();
    }
}
