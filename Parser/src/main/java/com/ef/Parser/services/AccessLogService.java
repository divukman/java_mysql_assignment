package com.ef.Parser.services;

import com.ef.Parser.entities.AccessLog;
import com.ef.Parser.repositories.AccessLogsRepository;
import com.ef.Parser.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.datasource.url}")
    private String URL;

    @Value("${spring.datasource.username}")
    private String USERNAME;

    @Value("${spring.datasource.password}")
    private String PASSWORD;


    /**
     * @deprecated Slow performance. Could not get it working as fast as prepared statements.
     * Saves logs to the DB, batch of 500 at a time.
     * @param logs
     */
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

    /**
     * Saves the logs to the DB using JDBC connection and prepared statements.
     * @param logs
     */
    public void saveLogsBatch(final List<AccessLog> logs) {
        Connection con = null;
        PreparedStatement ps = null;
        String query = "insert into access_log (id, timestamp, ip, method, status_code, client) values (?,?,?,?,?,?)";
        try {
            con = DBUtil.getConnection(URL, USERNAME, PASSWORD);
            ps = con.prepareStatement(query);

            final long start = System.currentTimeMillis();
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
