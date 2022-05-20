package com.fastcampus.board.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long seq;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime regDate;
    private int cnt;
}
