package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository extends CrudRepository<VoteEntity,Integer> {
    @Query(value = "select * from vote where vote_time between :timeStart and :timeEnd", nativeQuery = true)
    List<VoteEntity> findRoundVotesTime(@Param("startTime") LocalDateTime startTime, @Param("endTime")LocalDateTime endTime);
}
