package com.ef.Parser;


import com.ef.Parser.entities.BlackList;
import com.ef.Parser.exceptions.ArgumentException;
import com.ef.Parser.repositories.AccessLogsRepository;
import com.ef.Parser.services.AccessLogService;
import com.ef.Parser.services.BlackListService;
import com.ef.Parser.util.Arguments;
import com.ef.Parser.util.ArgumentsValidator;
import com.ef.Parser.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@Slf4j
public class ParserApplication {

    @Autowired
    AccessLogService accessLogService;

    @Autowired
    BlackListService blackListService;

	public static void main(String[] args) {
		SpringApplication.run(ParserApplication.class, args);
	}


    /**
     * Parse the command line arguments with the command line runner.
     * @return processing method
     */
	@Bean
    CommandLineRunner runner () {
		return args -> {
		    // Log startup info
            log.info("ParserApplication: starting the application...");
            log.info("arguments passed: ");
            for (String arg : args) {
                log.info(" ---> " + arg);
            }

            // Parse the arguments
            Arguments arguments;
            try {
                arguments = ArgumentsValidator.validate(args);
            } catch(ArgumentException e) {
                log.error("Missing required arguments" + e.getMessage());
                return; //@todo: refactor
            }

            // If log file was specified, load data to the database
            if (arguments.getLogfile() != null) {
                FileUtil.readLogsFileToDB(arguments.getLogfile(), accessLogService);
            }

            // Execute a query specified by the command line arguments
            log.info("------------------------ executing query -------------------------------");
            List<String> lstIps = accessLogService.findByCustomQuery(arguments.getStartDate(),
                    arguments.getDuration().equalsIgnoreCase(ArgumentsValidator.HOURLY) ? arguments.getStartDate().plusHours(1) : arguments.getStartDate().plusDays(1),
                    arguments.getThreshold());

            ArrayList<BlackList> lstBlackList = new ArrayList<>();

            lstIps.stream().forEach(s-> {
                // Log
                final String msg = "IP: " + s + " banned for making >= " + arguments.getThreshold() + " requests, from: " + arguments.getStartDate() + " in specified duration: " + arguments.getDuration();
                log.info(msg);

               BlackList blackListItem = new BlackList();
               blackListItem.setIp(s);
               blackListItem.setReason(msg);
               lstBlackList.add(blackListItem);
            });

            blackListService.saveBlacklist(lstBlackList);
            log.info("------------------------ done -------------------------------");
        };
	}
}
