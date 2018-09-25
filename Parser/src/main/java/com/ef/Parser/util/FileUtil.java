package com.ef.Parser.util;

import com.ef.Parser.entities.AccessLog;
import com.ef.Parser.repositories.AccessLogsRepository;
import com.ef.Parser.services.AccessLogService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class FileUtil {

    /**
     * Reads the logs file and stores the data to the access logs database table.
     * @param fileName logs file name
     */
    public static void readLogsFileToDB(final String fileName, final AccessLogService accessLogService) throws IOException {
        final File file = new File(fileName);

        // Check out if file exists
        if (!file.exists()) {
            throw new FileNotFoundException("File: " + fileName + " was not found!");
        }

        List<AccessLog> lstLogs = new ArrayList<>();

        final long startts = System.currentTimeMillis();
        log.info("Opening file stream: " + startts);
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                System.out.println(line);


                String [] tokens = line.split("\\|");
                if (tokens.length != 5) {
                    throw new RuntimeException("Invalid log line: " + line);
                }

                AccessLog accessLog = new AccessLog();
                accessLog.setTimestamp(DateUtil.validateLogDate(tokens[0]));
                accessLog.setIp(tokens[1]);
                accessLog.setMethod(tokens[2]);
                accessLog.setStatusCode(Integer.valueOf(tokens[3]));
                accessLog.setClient(tokens[4]);

                lstLogs.add(accessLog);
            });
        } catch (IOException e) {
            final String msg = "Error reading in the file: " + fileName;
            log.error(msg);
            throw new IOException(msg + e.getMessage());
        }

        log.info("Loading from file to memory took: " + (System.currentTimeMillis() - startts));

        log.info("Saving the logs to the database...");
        accessLogService.saveLogsBatch(lstLogs);
        log.info("Saving the logs to the database took: " + (System.currentTimeMillis() - startts));
    }

    public static void main(String args[]) {
        final String str = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n";
        String [] tokens = str.split("\\|");
        System.out.println("---------------- TOKENIZIRANO:");
        for (String token : tokens) {
            System.out.println("*********** " + token);
        }
    }

}
