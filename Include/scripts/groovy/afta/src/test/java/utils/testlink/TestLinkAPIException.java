package utils.testlink;

/**
 * The exception is used to indicate the conditions under which a failure occurred during a call to a TestLink API Java Client method.
 * 
 */
public class TestLinkAPIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create exception with a message.
	 * 
	 * @param msg
	 *            : API Message
	 */
	public TestLinkAPIException(String msg) {
		super(msg);
	}

	/**
	 * Create a nested exception with a new message.
	 * 
	 * @param msg
	 *            : API Message
	 * @param e
	 *            : Throwable
	 */
	public TestLinkAPIException(String msg, Throwable e) {
		super(msg, e);
	}
}
