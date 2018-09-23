package com.ef.Parser.util;


import com.ef.Parser.exceptions.ArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;


/**
 * Class used for validating the user argument's input.
 */

@Slf4j
public class ArgumentsValidator {

    /**
     * Constants.
     */
    public static final String ARG_LOG_FILE = "logFile";
    public static final String ARG_START_DATE = "startDate";
    public static final String ARG_DURATION = "duration";
    public static final String ARG_TRESHOLD = "threshold";

    public static final String HOURLY = "hourly";
    public static final String DAILY = "daily";

    /**
     * Validates the passed arguments and returns a holder object.
     * @param args
     * @return Arguments object holding the arguments
     */
    public static Arguments validate(String... args) throws ParseException {
        Arguments result = null;

        final Options options = new Options();
        options.addOption(Option.builder().longOpt(ARG_LOG_FILE).required(true).hasArg(true).build());
        options.addOption(Option.builder().longOpt(ARG_START_DATE).required(true).hasArg(true).build());
        options.addOption(Option.builder().longOpt(ARG_DURATION).required(true).hasArg(true).build());
        options.addOption(Option.builder().longOpt(ARG_TRESHOLD).required(true).hasArg(true).build());


        final CommandLineParser commandLineParser = new DefaultParser();
        final CommandLine commandLine;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            log.info("---------------------------------------------------------");
            log.error("Error validating command line arguments: " + e);
            log.info("---------------------------------------------------------");
            throw new ArgumentException(e.getMessage());
        }

        final String logFile = commandLine.getOptionValue(ARG_LOG_FILE);
        final String startDate =  commandLine.getOptionValue(ARG_START_DATE);
        final String duration = commandLine.getOptionValue(ARG_DURATION);
        final String threshold = commandLine.getOptionValue(ARG_TRESHOLD);

        log.info("arguments parsed: ");
        log.info("---------------------------------------------------------");
        log.info(ARG_LOG_FILE + ": " + logFile);
        log.info(ARG_START_DATE + ": " + startDate);
        log.info(ARG_DURATION + ": " + duration);
        log.info(ARG_TRESHOLD + ": " + threshold);
        log.info("---------------------------------------------------------");

        result = new Arguments();
        result.setStartDate(DateUtil.validateDate(startDate));
        result.setDuration(ArgumentsValidator.validateDuration(duration));
        result.setThreshold(ArgumentsValidator.validateThreshold(threshold));
        result.setLogfile(logFile);

        return result;
    }


    /**
     * Validates duration argument.
     * @param strDuration
     * @return duration string, if valid
     * @throws ArgumentException if duration string is invalid
     */
    public static String validateDuration(final String strDuration) {
        if ( !(strDuration.equalsIgnoreCase(HOURLY) || strDuration.equalsIgnoreCase(DAILY)) ) {
            throw new ArgumentException(ARG_DURATION + " values should be: " + HOURLY + " or " + DAILY);
        }
        return strDuration;
    }

    /**
     * Validates the threshold argument. Must be an integer number.
     * @param strThreshold
     * @return Integer value.
     * @throws ArgumentException if argument is not an integer number
     */
    public static Integer validateThreshold(final String strThreshold) {
       Integer result = null;

       try {
           result = Integer.valueOf(strThreshold);
        } catch(Exception e) {
            throw new ArgumentException("threshold should be a number. " +  e.getMessage());
        }

       return result;
    }


}
