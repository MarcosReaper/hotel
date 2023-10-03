package com.capitole.hotel.core.repository;

import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;

import java.util.concurrent.ExecutionException;

public interface ReservationReplySender {

    String execute(SearchRequestDTO searchRequestDTO) throws ExecutionException, InterruptedException;
}
