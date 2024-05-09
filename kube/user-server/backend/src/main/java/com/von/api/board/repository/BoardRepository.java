package com.von.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.von.api.board.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
