package com.fastcampus.board.Repository;

import com.fastcampus.board.Entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    BoardEntity findById(long id);
}
