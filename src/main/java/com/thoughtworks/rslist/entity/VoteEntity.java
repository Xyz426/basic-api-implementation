package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vote")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column
    private int tickets;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "rs_event_id")
    private int rsEventId;

    @Column(name = "vote_time")
    private LocalDateTime voteTime;
}
