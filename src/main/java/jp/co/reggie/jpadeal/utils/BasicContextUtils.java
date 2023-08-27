package jp.co.reggie.jpadeal.utils;

import java.time.LocalDateTime;

import jp.co.reggie.jpadeal.entity.BasicEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 基於ThreadLocal封裝工具類，用於獲取和保存當前用戸ID；
 *
 * @author Administrator
 * @since 2022-11-18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BasicContextUtils {

	private static Long currentId;

	public static void setCurrentId(final Long tangledID) {
		currentId = tangledID;
	}

	private static Long getCurrentId() {
		return currentId;
	}

	public static Long getOnceId() {
		final Long OnceId = getCurrentId();
		setCurrentId(0L);
		return OnceId;
	}

	/**
	 * 通用保存處理
	 *
	 * @param aEntity 實體類
	 */
	public static void fillWithInsert(final BasicEntity aEntity) {
		aEntity.setId(SnowflakeUtils.nextId());
		aEntity.setCreationUser(getCurrentId());
		aEntity.setUpdatingUser(getCurrentId());
		aEntity.setCreationTime(LocalDateTime.now());
		aEntity.setUpdatingTime(LocalDateTime.now());
	}

	/**
	 * 通用更新處理
	 *
	 * @param aEntity 實體類
	 */
	public static void fillWithUpdate(final BasicEntity aEntity) {
		aEntity.setUpdatingUser(getCurrentId());
		aEntity.setUpdatingTime(LocalDateTime.now());
	}
}
