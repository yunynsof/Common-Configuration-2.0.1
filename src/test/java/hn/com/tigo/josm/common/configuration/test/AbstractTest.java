package hn.com.tigo.josm.common.configuration.test;

import javax.ejb.embeddable.EJBContainer;

/**
 * The class AbstractTest contains the <Usage of this class> for PromotionEngine
 * project.
 *
 * @author Gary Gonzalez Zepeda <ggzepeda@latam.stefanini.com/>
 * @version 1.0.0
 * @since Feb 11, 2017
 */
public abstract class AbstractTest {

	/** The container. */
	private static EJBContainer container;

	/**
	 * Instantiates a new abstract test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public AbstractTest() throws Exception {
		container = EjbContainerContext.INSTANCE.getContainer();
	}

	/**
	 * Method responsible to gets the container.
	 *
	 * @return the container
	 */
	public static EJBContainer getContainer() {
		return container;
	}

}
