package jp.co.reggie.jpadeal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 基於ThreadLocal封裝工具類，用於獲取和保存當前用戸ID；
 *
 * @author Administrator
 * @since 2022-11-18
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicContextUtils {

	private static Long currentId;

	protected static void setCurrentId(final Long tangledID) {
		currentId = tangledID;
	}

	public static Long getCurrentId() {
		return currentId;
	}

	public static Long getGeneratedId() {
		return SnowflakeUtils.nextId();
	}
}
