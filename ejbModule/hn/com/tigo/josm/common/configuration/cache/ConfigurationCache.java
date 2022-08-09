package hn.com.tigo.josm.common.configuration.cache;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import hn.com.tigo.josm.common.cache.Cache;
import hn.com.tigo.josm.common.cache.ObjectFactoryCache;
import hn.com.tigo.josm.common.configuration.dto.ConfigutationDto;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote;
import hn.com.tigo.josm.common.interfaces.producer.InterfaceFactory;
import hn.com.tigo.josm.common.locator.ServiceLocator;
import hn.com.tigo.josm.common.locator.ServiceLocatorException;

/**
 * This class makes use of the ConfigurationDTO class, 
 * and makes use of the cache settings.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 28/05/2018 10:33:47 AM 2018
 */
public class ConfigurationCache extends Cache<ConfigutationDto, ConfigurationException> {

	/** Attribute that determine log. */
	private static final transient Logger LOGGER = Logger.getLogger(ConfigurationCache.class);

	/** Attribute that determine a Constant of CACHE. */
	private static final ConfigurationCache CACHE = new ConfigurationCache();

	/**
	 * Instantiates a new configuration cache.
	 */
	private ConfigurationCache() {
		super("./properties/common/configuration.json");
	}

	/**
	 * Gets the single instance of ConfigurationCache.
	 *
	 * @return single instance of ConfigurationCache
	 */
	public static ConfigurationCache getInstance() {
		return CACHE;
	}
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.cache.Cache#loadData()
	 */
	@Override
	protected ConfigutationDto loadData() throws ConfigurationException {
		final ServiceLocator locator = ServiceLocator.getInstance();
		final ObjectMapper objectMapper = ObjectFactoryCache.getInstance().getObjectMapper();
		ConfigutationDto configutationDto = null;

		try {
			final ConfigurationJosmRemote configurationJOSM = locator
					.getService(InterfaceFactory.COMMON_CONFIGURATION_REMOTE);
			final byte[] result = configurationJOSM.getFileFromSystem(this.path);
			configutationDto = objectMapper.readValue(result, ConfigutationDto.class);
			this.expiration = configutationDto.getConfigExpiration();
		} catch (IOException | ServiceLocatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConfigurationException(e.getMessage(), e);
		}

		return configutationDto;
	}

}
