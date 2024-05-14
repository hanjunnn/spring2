package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.dto.DTOBoard;
import com.example.entity.EntityBoard;
import com.example.repository.BoardRepository;

@Service
public class ServiceBoard {
    @Autowired
    BoardRepository boardRepo;

    public Iterable<EntityBoard> getAllBoard() {
        return boardRepo.findAll();
    }
    public void Write(DTOBoard board) {
        System.out.println(board.Board_Title);
        System.out.println(board.Board_Content);
        System.out.println(board.Board_Author);
        System.out.println(board.Board_Date);
        boardRepo.save(board.ToEntity());
    }


}
