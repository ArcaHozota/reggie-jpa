package jp.co.reggie.jpadeal.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 菜品口味數據傳輸專用類
 *
 * @author ArkamaHozota
 * @since 3.10
 */
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
}
