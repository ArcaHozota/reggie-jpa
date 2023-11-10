package jp.co.reggie.jpadeal.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 分類管理數據傳輸類
 *
 * @author ArkamaHozota
 * @since 3.64
 */
@Data
public final class CategoryDto implements Serializable {

	private static final long serialVersionUID = -38773020262090206L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 類型：1、菜品分類；2、套餐分類；
	 */
	private Integer type;

	/**
	 * 分類名稱
	 */
	private String name;

	/**
	 * 順序
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
}
