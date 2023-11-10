package jp.co.reggie.jpadeal.common;

/**
 * 業務ロジック例外クラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public class ReggieException extends RuntimeException {

	private static final long serialVersionUID = 1692201139310590555L;

	public ReggieException(final String message) {
		super(message);
	}
}
