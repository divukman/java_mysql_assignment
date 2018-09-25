package com.ef.Parser.repositories;

import com.ef.Parser.entities.AccessLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface AccessLogsRepository extends CrudRepository <AccessLog, Long> {
    @Query( "select LOGLINE.ip from AccessLog LOGLINE " +
            "where LOGLINE.timestamp between :startts and :endts " +
            "group by LOGLINE.ip having count(LOGLINE.ip) >= :threshold" )
    List<String> findByCustomQuery(final @Param("startts") LocalDateTime startts, final @Param("endts") LocalDateTime endts, final @Param("threshold") Long threshold);

}
