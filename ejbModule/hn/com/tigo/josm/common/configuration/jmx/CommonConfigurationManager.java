package hn.com.tigo.josm.common.configuration.jmx;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.configuration.cache.ConfigurationCache;
import hn.com.tigo.josm.persistence.exception.PersistenceException;
import hn.com.tigo.josm.persistence.interfaces.ServiceSessionRemote;

/**
 * The class CommonConfigurationManager contains the <Usage of this class> for
 * Common-Configuration project.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Oct 25, 2017
 */
@Singleton
@LocalBean
@Startup
public class CommonConfigurationManager implements CommonConfigurationMXBean {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(CommonConfigurationManager.class);

	/** The platform M bean server. */
	private MBeanServer _platformMBeanServer;

	/** The object name. */
	private ObjectName _objectName;

	/** The service session. */
	@EJB
	private ServiceSessionRemote serviceSession;
	
	/** Attribute that determine cache. */
	private ConfigurationCache cache = ConfigurationCache.getInstance();

	/**
	 * Register in JMX.
	 */
	@PostConstruct
	public void registerInJMX() {
		try {
			this._objectName = new ObjectName("hn.com.tigo.josm.CacheManager:type=CommonConfigurationCacheManager");
			this._platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
			this._platformMBeanServer.registerMBean(this, this._objectName);
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException
				| NotCompliantMBeanException e) {
			LOGGER.error("Failed to register the debugger bean in the JMX", e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * Unregister from JMX.
	 */
	@PreDestroy
	public void unregisterFromJMX() {
		try {
			this._platformMBeanServer.unregisterMBean(this._objectName);
		} catch (MBeanRegistrationException | InstanceNotFoundException e) {
			LOGGER.error("Problem during unregistration of monitoring into JMX ", e);
			throw new IllegalStateException("Problem during unregistration of monitoring into JMX ", e);
		}
		LOGGER.info("ConfigurationManager has been destroyed");
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.configuration.jmx.CommonConfigurationMXBean#resetConfiguration()
	 */
	public void resetConfiguration() {
		try {
			this.cache.reset();
			this.serviceSession.resetSessionBaseConfiguration("ConfigurationJosm");
		} catch (PersistenceException e) {
			LOGGER.info(e.getMessage(), e);
		}
	}
}
