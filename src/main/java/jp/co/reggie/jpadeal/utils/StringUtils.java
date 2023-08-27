package jp.co.reggie.jpadeal.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.lang.Nullable;

/**
 * 共通ストリング判断ツール
 *
 * @author Administrator
 * @since 2023-07-11
 */
public final class StringUtils {

	/**
	 * UTF-8キャラセット
	 */
	public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

	/**
	 * 空のストリング
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * コンストラクタ
	 */
	private StringUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 当ストリングは空かどうかを判断する
	 *
	 * @param str ストリング
	 * @return true: 空, false: 空ではない
	 */
	public static boolean isEmpty(@Nullable final String str) {
		return (str == null || str.length() == 0 || str.isBlank());
	}

	/**
	 * 当ストリングは空ではないかどうかを判断する
	 *
	 * @param str ストリング
	 * @return true: 空ではない, false: 空
	 */
	public static boolean isNotEmpty(@Nullable final String str) {
		return !isEmpty(str);
	}

	/**
	 * 二つのストリングはイコールすることを判断する
	 *
	 * @param str1 ストリング1
	 * @param str2 ストリング2
	 * @return true: イコール, false: イコールしない
	 */
	public static boolean isEqual(@Nullable final String str1, @Nullable final String str2) {
		final boolean isEqual;
		if (str1 == null && str2 == null) {
			return true;
		} else if (str1 == null || str2 == null) {
			return false;
		} else if (str1.length() != str2.length()) {
			return false;
		} else {
			isEqual = str1.trim().equals(str2.trim());
		}
		return isEqual;
	}

	/**
	 * 二つのストリングはイコールしないことを判断する
	 *
	 * @param str1 ストリング1
	 * @param str2 ストリング2
	 * @return true: イコールしない, false: イコール
	 */
	public static boolean isNotEqual(@Nullable final String str1, @Nullable final String str2) {
		return !isEqual(str1, str2);
	}
}