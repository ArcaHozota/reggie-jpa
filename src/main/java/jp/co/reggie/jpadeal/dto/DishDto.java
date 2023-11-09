package jp.co.reggie.jpadeal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * 菜品以及口味數據傳輸專用類
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Data
public final class DishDto {

	/**
	 * 菜品ID
	 */
	private Long id;

	/**
	 * 菜品名稱
	 */
	private String name;

	/**
	 * 菜品分類ID
	 */
	private Long categoryId;

	/**
	 * 菜品價格
	 */
	private BigDecimal price;

	/**
	 * 商品碼
	 */
	private String code;

	/**
	 * 圖片
	 */
	private String image;

	/**
	 * 描述信息
	 */
	private String description;

	/**
	 * 菜品售賣狀態：1:在售, 0:停售;
	 */
	private Integer status;

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

	/**
	 * 口味集合
	 */
	private List<DishFlavorDto> dishFlavours;

	/**
	 * 分類名稱
	 */
	private String categoryName;

	/**
	 * 複製品
	 */
	private Integer copy;
}