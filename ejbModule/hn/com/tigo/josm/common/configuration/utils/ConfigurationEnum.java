package hn.com.tigo.josm.common.configuration.utils;

/**
 * The enum ConfigurationEnum contains the code, message and details for
 * Common-Configuration project.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Apr 7, 2017
 */
public enum ConfigurationEnum {

	/** The persist msg. */
	PERSIST_MSG(0, "Operation successfull", "The configuration has been stored in database"),

	/** The get msg error. */
	GET_MSG_MISSING(1, "Missing configuration file", "The configuration file does not exists"),

	/** The special caracters error. */
	SPECIAL_CARACTERS_ERROR(2, "Special caracters", "The File Name must not contain special caracters."),

	/** The file name size error. */
	FILE_NAME_SIZE_ERROR(3, "Filename size", "The size of the File Name must be %s caractes lenght"),

	/** The json msg error. */
	JSON_MSG_ERROR(4, "Invalid JSON", "The JSON object contained in the fileConfiguration attribute is invalid."),

	/** The param error msg. */
	PARAM_ERROR_MSG(5, "Missing parameter: %s", "The %s is missing"),

	/** The db error msg. */
	DB_ERROR_MSG(6, "Database issue", "There is a problem with the connection in Database"),
	
	/** Attribute that determine OAUTH_ERROR_MSG. */
	OAUTH_ERROR_MSG(7, "Invalid Token", "The Token is invalid, cannot process operation."),
	
	/** Attribute that determine VALIDATION_ERROR_MSG. */
	VALIDATION_ERROR_MSG(8, "Invalid configuration JSON", "The json has attributes that cannot be null or empty."),
	
	/** Attribute that determine EMPTY_MSG. */
	EMPTY_MSG(8, "No data", "No data was found."),
	
	/** Attribute that determine SPECIAL_CHARACTERS_ERROR_MSG. */
	SPECIAL_CHARACTERS_ERROR_MSG(5, "Invalid special characters", "The %s must not contain special characters.");

	/** The code. */
	private final int code;

	/** The message. */
	private final String message;

	/** The message detail. */
	private final String messageDetail;

	/**
	 * Instantiates a new configuration enum.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param messageDetail
	 *            the message detail
	 */
	private ConfigurationEnum(int code, String message, String messageDetail) {
		this.code = code;
		this.message = message;
		this.messageDetail = messageDetail;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the message detail.
	 *
	 * @return the message detail
	 */
	public String getMessageDetail() {
		return messageDetail;
	}

}
