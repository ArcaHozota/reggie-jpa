package jp.co.reggie.jpadeal.dto;

import java.util.List;

import jp.co.reggie.jpadeal.entity.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜品以及口味數據傳輸專用類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class DishDto {

	/**
	 * 口味集合
	 */
	private List<DishFlavor> flavors;

	/**
	 * 分類名稱
	 */
	private String categoryName;

	/**
	 * 複製品
	 */
	private Integer copy;
}