package jp.co.reggie.jpadeal.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 通用返回結果類
 *
 * @author Administrator
 */
@Getter
@Setter
@NoArgsConstructor
public final class Reggie<T> {

	/**
	 * 處理成功的信息
	 */
	private static final String SUCCESS = "SUCCESS";

	/**
	 * 處理失敗的信息
	 */
	private static final String ERROR = "ERROR";

	/**
	 * 編碼： 成功：SUCCESS，失敗：ERROR
	 */
	private String code;

	/**
	 * 錯誤信息
	 */
	private String msg;

	/**
	 * 數據
	 */
	private T data;

	/**
	 * 處理成功
	 *
	 * @param object 對象
	 * @param <T>    汎型
	 * @return 返回的對象
	 */
	public static <T> Reggie<T> success(final T object) {
		final Reggie<T> reggie = new Reggie<>();
		reggie.data = object;
		reggie.code = SUCCESS;
		return reggie;
	}

	/**
	 * 請求失敗
	 *
	 * @param msg 請求失敗的信息
	 * @param <T> 汎型
	 * @return 失敗的信息
	 */
	public static <T> Reggie<T> error(final String msg) {
		final Reggie<T> reggie = new Reggie<>();
		reggie.msg = msg;
		reggie.code = ERROR;
		return reggie;
	}
}
