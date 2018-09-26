package com.ef.Parser.services;

import com.ef.Parser.entities.BlackList;
import com.ef.Parser.repositories.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlackListService {

    @Autowired
    BlacklistRepository blacklistRepository;

    /**
     * Saves the balcklist of banned clients.
     * It is to be expected that this list will not be large, so we can use regular Spring Data JPA layer.
     * @param blackList
     */
    @Transactional
    public void saveBlacklist(final List<BlackList> blackList) {
        blacklistRepository.saveAll(blackList);
    }

    /**
     * Truncates the table.
     */
    public void truncate() {
        blacklistRepository.truncate();
    }
}
