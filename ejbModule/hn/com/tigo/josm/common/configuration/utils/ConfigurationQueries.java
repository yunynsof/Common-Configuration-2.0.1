package hn.com.tigo.josm.common.configuration.utils;

/**
 * The class ConfigurationQueries contains the Queries of the methods to
 * interact with ORACLE Data Base.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Mar 20, 2017
 */
public final class ConfigurationQueries {

	/** Attribute that stores the sql script that shows 
	 *  the data stored in the Document table in the database. */
	public static final String GET_CONFIGURATION = "select * from DOCUMENT where Name = ? order by Created desc";

	/** Attribute that determine a Constant of GET_FILE_NAMES. */
	public static final String GET_FILE_NAMES = "select * from DOCUMENT";

	/** Attribute that stores the sql script that updates the Document table, using the ID as parameter. */
	public static final String PERSIST_CONFIGURATION = "update DOCUMENT set Content = ?, Created = sysdate where ID = ?";

	/** Attribute that determine a Constant of PERSIST_EXECUTION_LOG_WITH_ID. */
	public static final String PERSIST_EXECUTION_LOG_WITH_ID = "insert into EXECUTION_LOG (Id, Adapter, LogUser, Request, LogDate, Ip) values(?,?,?,?,sysdate,?)";

	/** Attribute that determine a Constant of PERSIST_EXECUTION_LOG_WITHOUT_ID. */
	public static final String PERSIST_EXECUTION_LOG_WITHOUT_ID = "insert into EXECUTION_LOG (Adapter, LogUser, Request, LogDate, Ip) values(?,?,?,sysdate,?)";

	/**
	 * Instantiates a new configuration queries.
	 */
	private ConfigurationQueries() {
	}

}
