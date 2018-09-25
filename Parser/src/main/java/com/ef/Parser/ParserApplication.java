package com.ef.Parser;


import com.ef.Parser.exceptions.ArgumentException;
import com.ef.Parser.repositories.AccessLogsRepository;
import com.ef.Parser.util.Arguments;
import com.ef.Parser.util.ArgumentsValidator;
import com.ef.Parser.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@Slf4j
public class ParserApplication {

    @Autowired
    AccessLogsRepository accessLogsRepository;

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
            Arguments arguments = null;
            try {
                arguments = ArgumentsValidator.validate(args);
            } catch(ArgumentException e) {
                log.error("Missing required arguments" + e.getMessage());
            }

            //@todo: refactor
            if (arguments == null) {
                return;
            }

            // If log file was specified, load data to the database
            if (arguments.getLogfile() != null) {
                FileUtil.readLogsFileToDB(arguments.getLogfile(), accessLogsRepository);
            }

            // Execute a query specified by the command line arguments
            System.out.println("------------------------ executing query");
        };
	}
}
