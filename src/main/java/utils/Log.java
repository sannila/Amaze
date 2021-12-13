package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {

    //Initialize Log4j logs
    private static Logger log = Logger.getLogger(Log.class.getName());

    // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
    public static void startTestCase(String sTestCaseName) {
        log.info("****************************************************************************************");
        log.info("****************************************************************************************");
        log.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
        log.info("****************************************************************************************");
        log.info("****************************************************************************************");
    }

    //    This is to print log for the ending of the test case
    public static void endTestCase(String sTestCaseName) {
        log.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
        log.info("X");
        log.info("X");
        log.info("X");
        log.info("X");
    }

//    methods to be called
    public static void info(String message){
        log.info(message);
    }

    public static void warn(String message){
        log.info(message);
    }

    public static void fatal(String message){
        log.fatal(message);
    }

    public static void debug(String message){
        log.debug(message);
    }


}
