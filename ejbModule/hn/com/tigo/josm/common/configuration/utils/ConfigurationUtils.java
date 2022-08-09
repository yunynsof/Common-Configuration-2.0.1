package hn.com.tigo.josm.common.configuration.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.configuration.dto.ResponseJOSM;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;

/**
 * The class ConfigurationUtils contains the static methods that are useful for
 * Common-Configuration project.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Mar 27, 2017
 */
public final class ConfigurationUtils {

	/**
	 * This attribute will store an instance of log4j for ConfigurationJosm
	 * class.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(ConfigurationUtils.class);

	/** The pattern atribute to compile regex expressions. */
	private static Pattern pattern;

	static {
		pattern = Pattern.compile(ConfigurationConstants.REGEX_SPECIAL_CARACTERS);
	}

	/**
	 * Instantiates a new configuration utils.
	 */
	private ConfigurationUtils() {

	}

	/**
	 * Method that validates if a parameter is null or empty.
	 *
	 * @param name
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public static void validateField(final String name, final String value) throws ConfigurationException {
		if (value == null || "".equals(value)) {
			final String msgDetail = String.format(ConfigurationEnum.PARAM_ERROR_MSG.getMessageDetail(), name);
			LOGGER.error(msgDetail);
			
			final String msg = String.format(ConfigurationEnum.PARAM_ERROR_MSG.getMessage(), name);
			throw new ConfigurationException(msg, msgDetail);
		}

		final Matcher matcher = pattern.matcher(value);

		if (name.equals(ConfigurationConstants.FILE_NAME) && !matcher.matches()) {
			final String msgDetail = String.format(ConfigurationEnum.SPECIAL_CHARACTERS_ERROR_MSG.getMessageDetail(), name);
			LOGGER.error(msgDetail);
			
			throw new ConfigurationException(ConfigurationEnum.SPECIAL_CHARACTERS_ERROR_MSG.getMessage(), msgDetail);
		}

		if (name.equals(ConfigurationConstants.FILE_NAME) && value.length() > ConfigurationConstants.FILE_NAME_SIZE) {
			final String msg = String.format(ConfigurationEnum.FILE_NAME_SIZE_ERROR.getMessageDetail(), ConfigurationConstants.FILE_NAME_SIZE);
			LOGGER.error(msg);
			throw new ConfigurationException(ConfigurationEnum.FILE_NAME_SIZE_ERROR.getMessage(), msg);
		}
	}

	/**
	 * Gets the object response JOSM unmarshaling the XML file.
	 * 
	 * @return the ResponseJOSM child response josm
	 * @throws JAXBException
	 *             the JAXB exception
	 */
	public static ResponseJOSM getDtoResponseJosm(final File file) throws JAXBException {

		final JAXBContext jaxbContext = JAXBContext.newInstance(ResponseJOSM.class);
		final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		return ResponseJOSM.class.cast(jaxbUnmarshaller.unmarshal(file));
	}

	/**
	 * Method responsible to builds the parameters array.
	 *
	 * @param params
	 *            the params
	 * @param idGenerated
	 *            the id generated
	 * @return the object[]
	 */
	public static Object[] buildParameters(final Object[] params, final BigDecimal idGenerated) {
		final List<Object> arrayParams = new ArrayList<Object>();
		arrayParams.add(idGenerated);
		for (Object paramItem : params) {
			arrayParams.add(paramItem);
		}
		return arrayParams.toArray();
	}

}
