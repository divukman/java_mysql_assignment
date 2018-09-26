package com.ef.Parser.repositories;

import com.ef.Parser.entities.BlackList;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface BlacklistRepository extends CrudRepository <BlackList, Long> {

    @Query(value="truncate table black_list", nativeQuery = true)
    @Transactional
    @Modifying
    void truncate();

}
