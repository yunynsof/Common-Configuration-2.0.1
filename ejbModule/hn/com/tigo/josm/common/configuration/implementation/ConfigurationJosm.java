/**
 * ConfigurationJosm.java
 * Common-Configuration
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.configuration.implementation;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.configuration.dto.OauthResponse;
import hn.com.tigo.josm.common.configuration.dto.ResponseJOSM;
import hn.com.tigo.josm.common.configuration.oauth.OauthOperation;
import hn.com.tigo.josm.common.configuration.utils.ConfigurationEnum;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote;

/**
 * The class ConfigurationJosm contains the calls of the methods from
 * ConfigurationImpl class and expose them into a web service methods.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Mar 20, 2017
 */
@Stateless(mappedName = "ConfigurationJosm")
@WebService(serviceName = "ConfigurationService", endpointInterface = "hn.com.tigo.josm.common.interfaces.IConfigurationJosm")
public class ConfigurationJosm implements ConfigurationJosmRemote {
	
	/** Attribute that determine a Constant of LOGGER. */
	private static final transient Logger LOGGER = Logger.getLogger(ConfigurationJosm.class);

	/** The configuration implementation EJB injection. */
	@EJB
	private ConfigurationImpl configurationImpl;

	/**
	 * Create a ConfigurationJosm instance and load properties from file system.
	 */
	public ConfigurationJosm() {
	}
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote#getCurrentConfiguration(java.lang.String)
	 */
	@Override
	public String getCurrentConfiguration(final String fileName) throws ConfigurationException {

		return configurationImpl.getCurrentConfiguration(fileName);
	}
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote#persistConfiguration(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String persistConfiguration(final String fileName, final String fileConfiguration, final String token)
			throws ConfigurationException {
		
		final OauthOperation operation = new OauthOperation();				
		final OauthResponse response = operation.executeRestService(token);

		if (response.getMessage().equals("VALID TOKEN")) {
			return configurationImpl.persistConfiguration(fileName, fileConfiguration, response);
		}

		LOGGER.error(ConfigurationEnum.OAUTH_ERROR_MSG.getMessageDetail());
		throw new ConfigurationException(ConfigurationEnum.OAUTH_ERROR_MSG.getMessage(), ConfigurationEnum.OAUTH_ERROR_MSG.getMessageDetail());
	}
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote#getConfiguration(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ResponseJOSM getConfiguration(final String canonicalName)
			throws JAXBException, IOException, FileNotFoundException {

		return configurationImpl.getConfiguration(canonicalName);

	}
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote#getFileFromSystem(java.lang.String)
	 */
	@Override
	public byte[] getFileFromSystem(final String path) {
		return configurationImpl.getFileFromSystem(path);
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote#getFileNames()
	 */
	@Override
	public String getFileNames() throws ConfigurationException {		
		return configurationImpl.getFileNames();
	}

}
