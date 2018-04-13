package oata;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

public class hello {
    static Logger logger = Logger.getLogger(hello.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.info("Hello World");          // the old SysO-statement
    }
}

