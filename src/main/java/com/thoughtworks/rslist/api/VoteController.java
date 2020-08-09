package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VoteController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RsEventRepository rsEventRepository;

    @Autowired
    VoteRepository voteRepository;

    @PutMapping("rs/vote/{rsEventId}")
    public ResponseEntity addRsEvntVoteNum(@PathVariable int rsEventId, @RequestBody VoteEntity voteEntity) {
        int userId = voteEntity.getUserId();
        UserEntity userEntity = userRepository.findById(userId).get();

        int userTickets = userEntity.getTickets();
        int hopeVoteTickets = voteEntity.getTickets();

        if (userTickets < hopeVoteTickets) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

//        voteRepository.save(voteEntity);

        RsEventEntity rsEventEntity = rsEventRepository.findById(rsEventId).get();
        //更新事件票数：旧票+新票
        rsEventEntity.setTickets(rsEventEntity.getTickets() + hopeVoteTickets);

        userEntity.setTickets(userTickets - hopeVoteTickets);

        voteEntity.setUserId(userId);
        voteEntity.setRsEventId(rsEventId);

        voteEntity.setVoteTime(LocalDateTime.now());
        userEntity.setVoteId(voteEntity.getId());

        userRepository.save(userEntity);
        voteRepository.save(voteEntity);
        rsEventRepository.save(rsEventEntity);

        System.out.println(rsEventEntity.getTickets());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/vote/time")
    public ResponseEntity getRoundVoteTime(@RequestParam LocalDateTime startTime,
                                           @RequestParam LocalDateTime endTime){
        List<VoteEntity> votes = voteRepository.findRoundVotesTime(startTime, endTime);

        return ResponseEntity.ok(votes);
    }
}
