
package deploy;

import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author CosticaTeodor
 */
public class DeploymentConfiguration implements ServletContextListener {

    public static String PU_NAME = "CA2-deployment"; //USE the RIGHT name here 

    public void contextInitialized(ServletContextEvent sce) {
        Map<String, String> env = System.getenv();

        if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
            PU_NAME = "pu_OPENSHIFT";
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
