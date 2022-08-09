package hn.com.tigo.josm.common.configuration.test;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;

import hn.com.tigo.josm.common.interfaces.producer.InterfaceFactory;

/**
 * The class EjbContainerContext contains the <Usage of this class> for
 * PromotionEngine project.
 *
 * @author Gary Gonzalez Zepeda <ggzepeda@latam.stefanini.com/>
 * @version 1.0.0
 * @since Feb 11, 2017
 */
public final class EjbContainerContext implements Closeable {

	/** The Constant INSTANCE. */
	public static final EjbContainerContext INSTANCE = new EjbContainerContext();

	/** Attribute that determine a Constant of JUNIT. */
	public static final boolean JUNIT = false;

	/** The Constant RESOURCE. */
	private static final String RESOURCE = "new://Resource?type=DataSource";

	/** The Constant JDBCURL. */
	private static final String JDBCURL = "jdbc:oracle:thin:@192.168.159.52:1521/josmdb.celtel.net";

	/** The Constant DRIVER. */
	private static final String DRIVER = "oracle.jdbc.OracleDriver";

	/** The Constant PASS. */
	private static final String PASS = "j0sM";

	/** The container. */
	private static EJBContainer container;

	/**
	 * Instantiates a new ejb container context.
	 */
	private EjbContainerContext() {

		final Properties properties = new Properties();
		properties.put("ConfigurationDS", RESOURCE);
		properties.put("ConfigurationDS.JtaManaged", true);
		properties.put("ConfigurationDS.UserName", "CONFIGURATION");
		properties.put("ConfigurationDS.Password", "jsm_0101");
		properties.put("ConfigurationDS.JdbcUrl", JDBCURL);
		properties.put("ConfigurationDS.JdbcDriver", DRIVER);

		properties.put("ConfigurationDS2", RESOURCE);
		properties.put("ConfigurationDS2.JtaManaged", true);
		properties.put("ConfigurationDS2.UserName", "CONFIGURATION2");
		properties.put("ConfigurationDS2.Password", PASS);
		properties.put("ConfigurationDS2.JdbcUrl", JDBCURL);
		properties.put("ConfigurationDS2.JdbcDriver", DRIVER);

		container = EJBContainer.createEJBContainer(properties);

		InterfaceFactory.COMMON_CONFIGURATION_REMOTE = "java:global/Common-Configuration/ConfigurationJosm!hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote";

		if (JUNIT) {
			InterfaceFactory.MONITORING_MANAGER_REMOTE = "java:global/MonitoringManager/MonitoringManagerExternal!hn.com.tigo.josm.common.interfaces.MonitoringManagerRemote";
		} else {
			InterfaceFactory.MONITORING_MANAGER_REMOTE = "java:global/MonitoringManager-1.0.4/MonitoringManagerExternal!hn.com.tigo.josm.common.interfaces.MonitoringManagerRemote";
		}

	}

	/**
	 * Gets the container.
	 *
	 * @return the container
	 */
	public EJBContainer getContainer() {
		return container;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {

		if (container != null) {
			container.close();
		}
	}

}
