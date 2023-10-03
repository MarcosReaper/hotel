package com.capitole.hotel.core.usecase;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.repository.SearchIdReplySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class SendSearchIdAndRetrieveSearchUseCase {

    @Autowired
    private SearchIdReplySender searchIdReplySender;

    public Search execute(final String searchId) throws ExecutionException, InterruptedException {
        return searchIdReplySender.execute(searchId);
    }
}
