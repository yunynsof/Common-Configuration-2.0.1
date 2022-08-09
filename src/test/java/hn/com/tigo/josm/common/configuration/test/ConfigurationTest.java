/**
 * 
 */
package hn.com.tigo.josm.common.configuration.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.junit.Test;

import hn.com.tigo.josm.common.configuration.dto.ResponseJOSM;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote;

/**
 * The class ConfigurationTest contains the <Usage of this class> for
 * Common-Configuration project.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Mar 20, 2017
 */
public class ConfigurationTest extends AbstractTest {

	/**
	 * This attribute will store an instance of log4j for ConfigurationJosm class.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(ConfigurationTest.class);

	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "ScriptTaskConfig";

	/** The i configuration josm. */
	private static ConfigurationJosmRemote iConfigurationJosm;

	/**
	 * Instantiates a new configuration test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public ConfigurationTest() throws Exception {
		super();
		iConfigurationJosm = (ConfigurationJosmRemote) AbstractTest.getContainer().getContext()
				.lookup("java:global/Common-Configuration/ConfigurationJosm");
	}

	/**
	 * Gets the configuration test.
	 *
	 * @return the configuration test
	 */
	@Test
	public void getConfigurationTest() {
		String response;
		try {
			response = iConfigurationJosm.getCurrentConfiguration(FILE_NAME);
			LOGGER.info(response);
			assertTrue(response != null);
		} catch (ConfigurationException e) {
			LOGGER.error(e.getMessage());
			fail();
		}

	}

	/**
	 * Persist configuration.
	 */
	@Test
	public void persistConfiguration() {
		String response;
		try {
			final String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDIiLCJpYXQiOjE1Mjc1NTI2ODgsInN1YiI6Ijg2IiwiaXNzIjoiQ1BFUUEiLCJleHAiOjE4ODc1NTI2ODh9.qdHunuCxB9hCvSgLr27T-0AjCdUtWw64NaxU8Wzt1k4";
			response = iConfigurationJosm.persistConfiguration(FILE_NAME, "{\"timeout\": 100}", token);
			LOGGER.info(response);
			assertTrue(response != null);
		} catch (ConfigurationException e) {
			LOGGER.error(e.getMessage());
		}

	}

	@Test(expected = ConfigurationException.class)
	public void persistConfigurationWithoutFileName() throws ConfigurationException {

		final String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDIiLCJpYXQiOjE1Mjc1NTI2ODgsInN1YiI6Ijg2IiwiaXNzIjoiQ1BFUUEiLCJleHAiOjE4ODc1NTI2ODh9.qdHunuCxB9hCvSgLr27T-0AjCdUtWw64NaxU8Wzt1k4";
		iConfigurationJosm.persistConfiguration("\\", "{\"timeout\": 100}", token);

	}

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 * @throws JAXBException
	 *             the JAXB exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void getConfiguration() throws JAXBException, IOException {
		final ResponseJOSM response = iConfigurationJosm.getConfiguration("/persistence/partitions/ConfigurationJosm");
		LOGGER.info(response.getStatus().getStatus());
		assertTrue(response != null);
	}

	/**
	 * Gets the file from system.
	 *
	 * @return the file from system
	 */
	@Test
	public void getFileFromSystem() {
		byte[] response = iConfigurationJosm.getFileFromSystem("./properties/monitoring/listener.json");
		final String result = new String(response);
		LOGGER.info(result);
		assertTrue(response != null);
	}

	/**
	 * Gets the file names.
	 *
	 * @return the file names
	 * @throws JAXBException
	 *             the JAXB exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void getFileNames() throws JAXBException, IOException {
		String response;
		try {
			response = iConfigurationJosm.getFileNames();
			LOGGER.info(response);
			assertTrue(response != null);
		} catch (ConfigurationException e) {
			LOGGER.error(e.getMessage());
			fail();
		}
	}

}
