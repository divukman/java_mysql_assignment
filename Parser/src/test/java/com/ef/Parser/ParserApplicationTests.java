package com.ef.Parser;

import com.ef.Parser.util.Arguments;
import com.ef.Parser.util.ArgumentsValidator;
import com.ef.Parser.util.DateUtil;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserApplicationTests {

	@Test
	public void testArgumentsValidator() {
		final String [] args = {"--logFile=access.txt", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};
		Arguments arguments = null;
		try {
			arguments = ArgumentsValidator.validate(args);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assert (arguments != null);
		assert (arguments.getDuration().equalsIgnoreCase(ArgumentsValidator.HOURLY));
		assert(arguments.getLogfile().equalsIgnoreCase("access.txt"));
		assert(arguments.getStartDate().equals(DateUtil.validateDate("2017-01-01.13:00:00")));
		assert(arguments.getThreshold() == 100);
	}

}
