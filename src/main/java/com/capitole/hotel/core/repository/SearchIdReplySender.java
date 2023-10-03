package com.capitole.hotel.core.repository;

import com.capitole.hotel.core.entity.Search;

import java.util.concurrent.ExecutionException;

public interface SearchIdReplySender {

    Search execute(String searchId) throws ExecutionException, InterruptedException;
}
