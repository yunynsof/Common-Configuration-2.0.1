package hn.com.tigo.josm.common.configuration.implementation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hn.com.tigo.josm.common.cache.ObjectFactoryCache;
import hn.com.tigo.josm.common.configuration.dto.ConfigurationFileNameList;
import hn.com.tigo.josm.common.configuration.dto.OauthResponse;
import hn.com.tigo.josm.common.configuration.dto.ResponseJOSM;
import hn.com.tigo.josm.common.configuration.entities.ConfigurationEntity;
import hn.com.tigo.josm.common.configuration.entities.ExecutionLogEntity;
import hn.com.tigo.josm.common.configuration.utils.ConfigurationConstants;
import hn.com.tigo.josm.common.configuration.utils.ConfigurationEnum;
import hn.com.tigo.josm.common.configuration.utils.ConfigurationQueries;
import hn.com.tigo.josm.common.configuration.utils.ConfigurationUtils;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.persistence.core.ServiceSession;
import hn.com.tigo.josm.persistence.core.SessionBase;
import hn.com.tigo.josm.persistence.exception.PersistenceException;

/**
 * The class ConfigurationImpl contains the logic of the web services that gets
 * and persist from file and from Data Base the configuration from other
 * components.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Mar 21, 2017
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigurationImpl {

	/**
	 * This attribute will store an instance of log4j for ConfigurationJosm class.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(ConfigurationImpl.class);

	/** The properties object containing values loaded from properties file. */
	private static Properties properties;

	/** Attribute that determine the service session. */
	@EJB
	private ServiceSession serviceSession;

	/** The context of the transaction. */
	@Resource
	private EJBContext context;

	/** the session base. */
	private List<SessionBase> sessionBaseList;

	/** Attribute that determine properties file object reference. */
	private File file;

	static {

		try (final InputStream config = new FileInputStream(new File(ConfigurationConstants.PROPERTIES_PATH))) {

			properties = new Properties();
			properties.load(config);
		} catch (IOException e) {
			LOGGER.error(e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Instantiates a new configuration implementation.
	 */
	public ConfigurationImpl() {
	}

	/**
	 * Gets the file names.
	 *
	 * @return the file names
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public String getFileNames() throws ConfigurationException {

		SessionBase sessionBase = null;
		String response = "";

		try {

			sessionBase = serviceSession.getSessionBase(ConfigurationConstants.PARTITION_FILE);

			final ConfigurationEntity configurationEntity = new ConfigurationEntity(sessionBase);
			final List<ConfigurationEntity> configurationList = configurationEntity
					.executeSelectStatement(ConfigurationQueries.GET_FILE_NAMES, new Object[0]);

			if (!configurationList.isEmpty()) {
				final List<ConfigurationFileNameList> result = new ArrayList<>();
				for (ConfigurationEntity element : configurationList) {
					final ConfigurationFileNameList el = new ConfigurationFileNameList();
					el.setName(element.getFileName());
					el.setUpdated(element.getCreationDate());
					result.add(el);
				}
				response = ObjectFactoryCache.getInstance().getObjectMapper().writeValueAsString(result);
				LOGGER.info("The file name service was successfully loaded.");
			} else {
				response = ConfigurationEnum.DB_ERROR_MSG.getMessageDetail();
				LOGGER.info(ConfigurationEnum.DB_ERROR_MSG.getMessageDetail());
			}

		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConfigurationException(ConfigurationEnum.DB_ERROR_MSG.getMessage(), e.getMessage(), e);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConfigurationException(ConfigurationEnum.JSON_MSG_ERROR.getMessage(), e.getMessage(), e);
		} finally {
			this.closeSession(sessionBase);
		}

		return response;
	}

	/**
	 * Gets the current configuration of the component form Data Base.
	 *
	 * @param fileName
	 *            the file name of the configuration of the component.
	 * @return the current configuration response.
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public String getCurrentConfiguration(String fileName) throws ConfigurationException {

		ConfigurationUtils.validateField(ConfigurationConstants.FILE_NAME, fileName);

		SessionBase sessionBase = null;
		String response = "";
		final Object[] params = { fileName };

		try {

			sessionBase = serviceSession.getSessionBase(ConfigurationConstants.PARTITION_FILE);

			final ConfigurationEntity configurationEntity = new ConfigurationEntity(sessionBase);
			final List<ConfigurationEntity> configurationList = configurationEntity
					.executeSelectStatement(ConfigurationQueries.GET_CONFIGURATION, params);

			if (configurationList.isEmpty()) {
				LOGGER.error(ConfigurationEnum.GET_MSG_MISSING.getMessage());
				throw new ConfigurationException(ConfigurationEnum.GET_MSG_MISSING.getMessage(),
						ConfigurationEnum.GET_MSG_MISSING.getMessageDetail());
			}
			response = configurationList.get(0).getFileConfiguration();
			LOGGER.info("The file configuration was successfully loaded.");

		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConfigurationException(ConfigurationEnum.DB_ERROR_MSG.getMessage(), e.getMessage(), e);
		} finally {
			this.closeSession(sessionBase);
		}

		return response;

	}

	/**
	 * Persist the configuration of a component in Data Base.
	 *
	 * @param fileName
	 *            the file name of the configuration
	 * @param fileConfiguration
	 *            the file configuration in a JSON format.
	 * @param oauthResponse
	 *            the oauth response
	 * @return the string response of the method.
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public String persistConfiguration(final String fileName, final String fileConfiguration,
			final OauthResponse oauthResponse) throws ConfigurationException {

		ConfigurationUtils.validateField(ConfigurationConstants.FILE_NAME, fileName);
		ConfigurationUtils.validateField(ConfigurationConstants.FILE_CONFIG, fileConfiguration);

		String response = "";
		this.sessionBaseList = null;

		try {

			LOGGER.info("File Configuration: " + fileConfiguration);
			final ObjectMapper mapper = ObjectFactoryCache.getInstance().getObjectMapper();
			mapper.readTree(fileConfiguration);

			this.sessionBaseList = this.serviceSession.getSessionBaseList(ConfigurationConstants.PARTITION_FILE);

			boolean isFirst = true;
			BigDecimal configLogId = null;

			List<ConfigurationEntity> configurationList = null;
			final Object[] param = { fileName };
			final Object[] configLogParams = { fileName, oauthResponse.getUserName(), fileConfiguration,
					InetAddress.getLocalHost().getHostAddress() };

			for (SessionBase sessionBase : this.sessionBaseList) {
				if (isFirst) {
					isFirst = false;
					final ConfigurationEntity configurationEntity = new ConfigurationEntity(sessionBase);
					configurationList = configurationEntity
							.executeSelectStatement(ConfigurationQueries.GET_CONFIGURATION, param);

					this.validateEmptyData(configurationList);

					final ConfigurationEntity result = configurationList.get(0);
					final Object[] configParams = { fileConfiguration, result.getId() };
					configurationEntity.executeUpdateStatement(ConfigurationQueries.PERSIST_CONFIGURATION,
							configParams);

					final ExecutionLogEntity executionLogEntity = new ExecutionLogEntity(sessionBase);
					configLogId = (BigDecimal) executionLogEntity.executeInsertStatement(
							ConfigurationQueries.PERSIST_EXECUTION_LOG_WITHOUT_ID, configLogParams).get(0);
				} else {
					final ConfigurationEntity configurationEntity = new ConfigurationEntity(sessionBase);
					final ConfigurationEntity result = configurationList.get(0);
					final Object[] configParams = { fileConfiguration, result.getId() };
					configurationEntity.executeUpdateStatement(ConfigurationQueries.PERSIST_CONFIGURATION,
							configParams);

					final Object[] configParamsLogWithId = ConfigurationUtils.buildParameters(configLogParams,
							configLogId);
					final ExecutionLogEntity executionLogEntity = new ExecutionLogEntity(sessionBase);
					executionLogEntity.executeInsertStatement(ConfigurationQueries.PERSIST_EXECUTION_LOG_WITH_ID,
							configParamsLogWithId);
				}
			}

			response = ConfigurationEnum.PERSIST_MSG.getMessageDetail();

			LOGGER.info(ConfigurationEnum.PERSIST_MSG.getMessageDetail());

		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage(), e);
			this.context.setRollbackOnly();
			throw new ConfigurationException(ConfigurationEnum.DB_ERROR_MSG.getMessage(), e.getMessage(), e);
		} catch (IOException e1) {
			LOGGER.error(e1.getMessage(), e1);
			throw new ConfigurationException(ConfigurationEnum.JSON_MSG_ERROR.getMessage(),
					ConfigurationEnum.JSON_MSG_ERROR.getMessageDetail());

		} finally {
			this.closeSessionBase();
		}

		return response;

	}

	/**
	 * Gets the configuration from file of a component and cast the configuration
	 * object to JOSM Response.
	 *
	 * @param canonicalName
	 *            the canonical name of the file
	 * @return the configuration wrapped to a JOSM response.
	 * @throws JAXBException
	 *             the JAXB exception
	 * @deprecated method should not be used in future implementations.
	 */
	@Deprecated
	public ResponseJOSM getConfiguration(final String canonicalName) throws JAXBException {

		LOGGER.info("Process starts loading configuration data " + canonicalName);

		final String canonical = canonicalName.replaceAll(ConfigurationConstants.PACKAGE_BASE, "");
		final String directory = canonical.replace(".", File.separator);

		final StringBuffer absolutePath = new StringBuffer();
		absolutePath.append(properties.getProperty(ConfigurationConstants.ROUTE));
		absolutePath.append(directory);
		absolutePath.append(".xml");

		this.file = new File(absolutePath.toString());

		LOGGER.info("Find Process loading configuration data " + canonicalName + " exist " + this.file.exists());

		return ConfigurationUtils.getDtoResponseJosm(this.file);
	}

	/**
	 * Gets the file from the server and sends the byte array.
	 *
	 * @param path
	 *            the path from server
	 * @return the byte array from system
	 */
	public byte[] getFileFromSystem(String path) {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] buffer = new byte[1024];

		try (final FileInputStream stream = new FileInputStream(path)) {

			int index = 0;
			while ((index = stream.read(buffer)) != -1) {
				out.write(buffer, 0, index);
			}

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return out.toByteArray();
	}

	/**
	 * Close the session from Data Base.
	 *
	 * @param session
	 *            the session from Oracle data base.
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	private void closeSession(final SessionBase session) throws ConfigurationException {
		try {
			if (session != null) {
				session.close();
			}
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Closes all the sessionBaseList that are not null.
	 * 
	 * @throws ConfigurationException
	 *             the promotionException
	 */
	private void closeSessionBase() throws ConfigurationException {
		if (this.sessionBaseList != null) {
			this.closeSession();
		}
	}

	/**
	 * Close session.
	 *
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	private void closeSession() throws ConfigurationException {
		try {
			for (SessionBase sessionBase : this.sessionBaseList) {
				if (sessionBase != null) {
					sessionBase.close();
				}
			}
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Validate empty data.
	 *
	 * @param configurationList
	 *            the configuration list
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	private void validateEmptyData(final List<ConfigurationEntity> configurationList) throws ConfigurationException {
		if (configurationList.isEmpty()) {
			LOGGER.error(ConfigurationEnum.GET_MSG_MISSING.getMessage());
			throw new ConfigurationException(ConfigurationEnum.GET_MSG_MISSING.getMessage(),
					ConfigurationEnum.GET_MSG_MISSING.getMessageDetail());
		}
	}

}
