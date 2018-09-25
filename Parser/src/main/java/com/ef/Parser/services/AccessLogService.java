package com.ef.Parser.services;

import com.ef.Parser.entities.AccessLog;
import com.ef.Parser.repositories.AccessLogsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccessLogService {

    @Autowired
    AccessLogsRepository accessLogsRepository;


    public void saveLogs(final List<AccessLog> logs) {
        final int size = logs.size();
        int counter = 0;

        log.info("logs size: " + size);

        ArrayList<AccessLog> tmpLogs = new ArrayList<>();
        for (AccessLog log : logs) {
            tmpLogs.add(log);
            if ( (counter + 1 == size) || ( (counter + 1) % 500 == 0) ) {
                accessLogsRepository.saveAll(tmpLogs);
                tmpLogs.clear();
            }

            counter++;
        }
    }

    public void saveLogsBatch(final List<AccessLog> logs) {
        Connection con = null;
        PreparedStatement ps = null;
        String query = "insert into access_log (id, timestamp, ip, method, status_code, client) values (?,?,?,?,?,?)";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(query);

            long start = System.currentTimeMillis();
            for(int i =0; i<logs.size();i++){
                final AccessLog log = logs.get(i);

                ps.setInt(1, i);
                ps.setTimestamp(2, Timestamp.valueOf(log.getTimestamp()));
                ps.setString(3, log.getIp());
                ps.setString(4, log.getMethod());
                ps.setInt(5, log.getStatusCode());
                ps.setString(6, log.getClient());


                ps.addBatch();
                if(i%1000 == 0) ps.executeBatch();
            }
            ps.executeBatch();

            System.out.println("Time Taken="+(System.currentTimeMillis()-start));

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public List<String> findByCustomQuery(final LocalDateTime startts, final LocalDateTime endts, final  Long threshold) {
        return accessLogsRepository.findByCustomQuery(startts, endts, threshold);
    }



}
