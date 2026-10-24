package trcs.autorent.service;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Lookup<T> {
	@SuppressWarnings("unchecked")
	public T lookup(String ejbName, String interfaceName) {
        try {
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            InitialContext initialContext = new InitialContext(jndiProperties);
            
            // JNDI global exportado pelo WildFly para o EJB UserSB.
            return (T) initialContext.lookup(String.format("ejb:AutoRentEAR/AutoRent/%s!trcs.autorent.sessionbean.%s", ejbName, interfaceName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
