package jp.co.reggie.jpadeal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public final class DishFlavorDto {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 菜品ID
	 */
	private Long dishId;

	/**
	 * 口味名稱
	 */
	private String name;

	/**
	 * 口味數據List
	 */
	private String value;

	/**
	 * 創建時間
	 */
	private LocalDateTime createdTime;

	/**
	 * 更新時間
	 */
	private LocalDateTime updatedTime;

	/**
	 * 創建人
	 */
	private Long createdUser;

	/**
	 * 修改者
	 */
	private Long updatedUser;

	/**
	 * 邏輯刪除字段
	 */
	private String logicDeleteFlg;
}
