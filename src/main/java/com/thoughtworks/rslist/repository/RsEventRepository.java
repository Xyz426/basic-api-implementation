package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.RsEventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RsEventRepository extends CrudRepository<RsEventEntity,Integer> {
    @Override
    List<RsEventEntity> findAll();

    @Query(value = "select * from rs_event where id between ?1 and ?2",nativeQuery = true)
    List<RsEventEntity> findByRoundId(int start,int end);
//    @Transactional
//    void deleteAllByUserId(int userId);
}
