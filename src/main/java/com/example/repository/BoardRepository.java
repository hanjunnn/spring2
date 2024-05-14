package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.EntityBoard;


public interface BoardRepository extends CrudRepository <EntityBoard, Integer> {
    @Query(value = "select * from EntityBoard",
    nativeQuery = true)
    List<EntityBoard> findBoardData();
}
