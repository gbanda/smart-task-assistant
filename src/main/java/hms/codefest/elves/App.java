package hms.codefest.elves;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * App starter!
 *
 */
public class App implements WrapperListener{
    private static final Logger logger = LogManager.getLogger(Integer.class);
    private ClassPathXmlApplicationContext context;

    public static void main(String[] args) throws Exception {
        WrapperManager.start(new hms.codefest.elves.App(), args);
    }

    @Override
    public Integer start(String[] strings) {
        for (URL url : ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs()) {
            System.out.println(url.getFile());
        }
        logger.info("============================================");
        logger.info("=========== Starting Ajuba-3.0 =============");
        logger.info("============================================");
        context = new ClassPathXmlApplicationContext("spring-context.xml");
        context.registerShutdownHook();
        logger.info("============================================");
        logger.info("================ Started ===================");
        logger.info("============================================");
        return null;
    }

    @Override
    public int stop(int i) {
        context.stop();
        logger.info("===============================================================");
        logger.info("===================== Stopping Ajuba-3.0 ==========================");
        logger.info("===============================================================");
        return 0;
    }

    @Override
    public void controlEvent(int i) {

    }
}
