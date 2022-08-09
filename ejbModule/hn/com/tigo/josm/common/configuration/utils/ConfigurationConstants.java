package hn.com.tigo.josm.common.configuration.utils;

/**
 * The class ConfigurationConstants contains the constants for
 * Common-Configuration project.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Mar 20, 2017
 */
public final class ConfigurationConstants {

	/** Constant that determine the properties file path. */
	public static final String PROPERTIES_PATH = "./properties/josm.properties";

	/** Constant that determine package base. */
	public static final String PACKAGE_BASE = "hn.com.tigo.";

	/**
	 * Constant that determine the property key for the path of directory
	 * hierarchy in properties file.
	 */
	public static final String ROUTE = "route";

	/** The constant that determines the partition name of the common. */
	public static final String PARTITION_NAME = "ConfigurationJosm";

	/** The constant that refers to special characters. */
	public static final String REGEX_SPECIAL_CARACTERS = "[^\\\\/:?*\"<>|]*";

	/** The constant that determines the file name size. */
	public static final int FILE_NAME_SIZE = 100;

	/** Attribute that determines a partition file. */
	public static final String PARTITION_FILE = "ConfigurationJosm";

	/** Attribute that determine a Constant of configurtion path. */
	public static final String CONFIG_PATH = "./properties/configuration/josmConfiguration.properties";

	/** Attribute that determine a Constant of FILE_NAME. */
	public static final String FILE_NAME = "fileName";

	/** Attribute that determine a Constant of FILE_CONFIG. */
	public static final String FILE_CONFIG = "fileConfiguration";

	/**
	 * Construct method that instantiates a new configuration constants.
	 */
	private ConfigurationConstants() {

	}
}
