package com.gitr.services;

import com.gitr.dtos.ProviderDto;

import javax.transaction.Transactional;
import java.util.List;

public interface ProviderService {
    @Transactional
    List<String> addProvider(ProviderDto providerDto);

    List<String> providerLogin(ProviderDto providerDto);
}
