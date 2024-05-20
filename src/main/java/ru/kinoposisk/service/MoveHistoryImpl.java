package ru.kinoposisk.service;

import org.springframework.stereotype.Service;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.service.interfaces.MovieHistoryService;

import java.util.List;

@Service
public class MoveHistoryImpl implements MovieHistoryService {


    @Override
    public MovieHistory add(MovieHistory movieHistory) {
        return null;
    }

    @Override
    public void deleteByID(Long id) {

    }

    @Override
    public MovieHistory findByID(Long id) {
        return null;
    }

    @Override
    public List<MovieHistory> getAll() {
        return null;
    }

}
