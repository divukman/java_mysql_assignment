package com.ef.Parser.repositories;

import com.ef.Parser.entities.AccessLog;
import org.springframework.data.repository.CrudRepository;

public interface AccessLogsRepository extends CrudRepository <AccessLog, Long> {
}
