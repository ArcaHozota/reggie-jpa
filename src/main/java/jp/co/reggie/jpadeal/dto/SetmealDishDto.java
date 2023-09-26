package jp.co.reggie.jpadeal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public final class SetmealDishDto {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 套餐ID
	 */
	private Long setmealId;

	/**
	 * 菜品ID
	 */
	private Long dishId;

	/**
	 * 菜品名稱(冗餘字段)
	 */
	private String name;

	/**
	 * 菜品原價(冗餘字段)
	 */
	private BigDecimal price;

	/**
	 * 份數
	 */
	private Integer copies;

	/**
	 * 排序
	 */
	private Integer sort;

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
