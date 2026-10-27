package trcs.autorent.service;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Classe utilitária genérica responsável por realizar o lookup JNDI de Enterprise JavaBeans (EJBs) remotos no servidor WildFly.
 * 
 * @param <T> O tipo da interface remota do EJB
 */
public class Lookup<T> {
	
	/**
	 * Procura e retorna uma instância remota de um EJB através do seu nome e interface.
	 * 
	 * @param ejbName O nome do Bean Stateless/Stateful (ex: UserSB)
	 * @param interfaceName O nome da interface remota (ex: IUser)
	 * @return Uma instância proxy da interface remota do EJB, ou null em caso de erro
	 */
	@SuppressWarnings("unchecked")
	public T lookup(String ejbName, String interfaceName) {
        try {
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            InitialContext initialContext = new InitialContext(jndiProperties);
            
            // JNDI global exportado pelo WildFly para o EJB.
            return (T) initialContext.lookup(String.format("ejb:AutoRentEAR/AutoRent/%s!trcs.autorent.sessionbean.%s", ejbName, interfaceName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}