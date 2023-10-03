package com.capitole.hotel.entrypoint;

import com.capitole.hotel.core.usecase.SendReservationAndRetrieveSearchUseCase;
import com.capitole.hotel.core.usecase.SendSearchIdAndRetrieveSearchUseCase;
import com.capitole.hotel.entrypoint.dto.CountResponseDTO;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import com.capitole.hotel.entrypoint.dto.SearchResponseDTO;
import com.capitole.hotel.entrypoint.mapper.CountResponseMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class ReservationController {

    @Autowired
    private SendReservationAndRetrieveSearchUseCase sendReservationAndRetrieveSearchUseCase;

    @Autowired
    private SendSearchIdAndRetrieveSearchUseCase sendSearchIdAndRetrieveSearchUseCase;

    @Autowired
    private CountResponseMapper countResponseMapper;

    @PostMapping(value = "/search")
    public ResponseEntity<SearchResponseDTO> search(
            @Valid @RequestBody final SearchRequestDTO searchRequestDTO) throws ExecutionException,
            InterruptedException {

        var searchId = sendReservationAndRetrieveSearchUseCase.execute(searchRequestDTO);
        return ResponseEntity.ok(new SearchResponseDTO(searchId));
    }

    @GetMapping(value = "/count")
    public ResponseEntity<CountResponseDTO> count(@NotBlank @RequestParam(name = "searchId") String searchId)
            throws ExecutionException, InterruptedException {

        var search = sendSearchIdAndRetrieveSearchUseCase.execute(searchId);
        return ResponseEntity.ok(countResponseMapper.map(search));
    }
}
