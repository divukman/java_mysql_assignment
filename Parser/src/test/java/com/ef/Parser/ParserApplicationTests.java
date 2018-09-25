package com.ef.Parser;

import com.ef.Parser.entities.AccessLog;
import com.ef.Parser.repositories.AccessLogsRepository;
import com.ef.Parser.util.Arguments;
import com.ef.Parser.util.ArgumentsValidator;
import com.ef.Parser.util.DateUtil;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserApplicationTests {

	@Autowired
	AccessLogsRepository accessLogsRepository;


	@Test
	public void testArgumentsValidator() {
		final String [] args = {"--logFile=access.txt", "--startDate=2017-01-01.00:00:11", "--duration=hourly", "--threshold=100"};
		Arguments arguments = null;
		try {
			arguments = ArgumentsValidator.validate(args);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assert (arguments != null);
		assert (arguments.getDuration().equalsIgnoreCase(ArgumentsValidator.HOURLY));
		assert(arguments.getLogfile().equalsIgnoreCase("access.txt"));
		assert(arguments.getStartDate().equals(DateUtil.validateArgumentDate("2017-01-01.00:00:11")));
		assert(arguments.getThreshold() == 100L);
	}

	@Test
	public void testAccessLogsRepository() {
		AccessLog log = new AccessLog();
		log.setClient("dummy");
		log.setIp("127.0.0.1");
		log.setMethod("GET");
		log.setStatusCode(200);
		log.setTimestamp(DateUtil.validateLogDate("2017-01-01 00:00:11.763"));

		AccessLog savedLog = accessLogsRepository.save(log);
		assert (savedLog != null);

		accessLogsRepository.delete(savedLog);
	}

}
