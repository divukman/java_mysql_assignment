package com.ef.Parser.repositories;

import com.ef.Parser.entities.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlacklistRepository extends CrudRepository <BlackList, Long> {
}
