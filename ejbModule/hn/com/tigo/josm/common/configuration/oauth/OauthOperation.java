package hn.com.tigo.josm.common.configuration.oauth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import hn.com.tigo.josm.common.cache.ObjectFactoryCache;
import hn.com.tigo.josm.common.configuration.cache.ConfigurationCache;
import hn.com.tigo.josm.common.configuration.dto.ConfigutationDto;
import hn.com.tigo.josm.common.configuration.dto.OauthRequest;
import hn.com.tigo.josm.common.configuration.dto.OauthResponse;
import hn.com.tigo.josm.common.configuration.utils.ConfigurationEnum;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.common.exceptions.HttpClientException;

/**
 * This class makes use of the Map objects, HttpConnector, ConfigutationDto, 
 * ConfigurationCache, obtains attributes of these classes and uses them to execute the rest service.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 25/05/2018 03:42:54 PM 2018
 */
public class OauthOperation {

	/** Attribute that determine a Constant of LOGGER. */
	private static final transient Logger LOGGER = Logger.getLogger(OauthOperation.class);

	/** The properties. */
	private final Map<String, String> properties;

	/** The {@link HttpConnector} instance. */
	private final HttpConnector validateToken;

	/** Attribute that determine configutationDto. */
	private final ConfigutationDto configutationDto;

	/** Attribute that determine cache. */
	private ConfigurationCache cache = ConfigurationCache.getInstance();

	/**
	 * Instantiates a new oauth operation.
	 *
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public OauthOperation() throws ConfigurationException {
		this.properties = new HashMap<>();
		this.properties.put("Content-Type", "application/json");
		this.properties.put("Connection", "close");

		this.configutationDto = this.cache.retrieve();
		final Object[] params = { this.configutationDto.getGrant(), this.configutationDto.getUrl(),
				this.configutationDto.getUrlToken() };
		this.validateConfiguration(params);
		this.validateToken = new HttpConnector(this.configutationDto.getUrlToken(), this.properties);
	}

	/**
	 * Execute rest service.
	 *
	 * @return the oauth response
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public OauthResponse executeRestService(final String token) throws ConfigurationException {

		OauthResponse response = null;
		final ObjectMapper mapper = ObjectFactoryCache.getInstance().getObjectMapper();
		try {
			final OauthRequest oauthRequest = new OauthRequest();
			oauthRequest.setGrant(this.configutationDto.getGrant());
			oauthRequest.setPath(this.configutationDto.getUrl());
			oauthRequest.setToken(token);
			final String request = mapper.writeValueAsString(oauthRequest);
			final String result = this.validateToken.invoke(request, StandardCharsets.UTF_8);
			response = mapper.readValue(result, OauthResponse.class);
			LOGGER.info(result);
		} catch (IOException | HttpClientException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConfigurationException(e.getMessage(), e);
		}
		return response;
	}

	/**
	 * Validate configuration.
	 *
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	private void validateConfiguration(final Object[] params) throws ConfigurationException {

		for (Object element : params) {
			if (element == null || element.toString().isEmpty()) {
				LOGGER.error(ConfigurationEnum.VALIDATION_ERROR_MSG.getMessageDetail());
				throw new ConfigurationException(ConfigurationEnum.VALIDATION_ERROR_MSG.getMessage(),
						ConfigurationEnum.VALIDATION_ERROR_MSG.getMessageDetail());
			}
		}
	}

}
