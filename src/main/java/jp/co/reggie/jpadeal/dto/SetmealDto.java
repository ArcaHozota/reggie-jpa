package jp.co.reggie.jpadeal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * 套餐數據傳輸專用類
 *
 * @author Administrator
 */
@Data
public final class SetmealDto {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 分類ID
	 */
	private Long categoryId;

	/**
	 * 套餐名稱
	 */
	private String name;

	/**
	 * 套餐價格
	 */
	private BigDecimal price;

	/**
	 * 套餐售賣狀態：1:在售, 0:停售;
	 */
	private Integer status;

	/**
	 * 編碼
	 */
	private String code;

	/**
	 * 描述信息
	 */
	private String description;

	/**
	 * 圖片
	 */
	private String image;

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
	 * 套餐集合
	 */
	private List<SetmealDishDto> setmealDishes;

	/**
	 * 分類名稱
	 */
	private String categoryName;
}