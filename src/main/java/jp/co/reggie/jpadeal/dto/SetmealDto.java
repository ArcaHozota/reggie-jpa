package jp.co.reggie.jpadeal.dto;

import java.util.List;

import jp.co.reggie.jpadeal.entity.SetmealDish;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 套餐數據傳輸專用類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class SetmealDto {

	/**
	 * 套餐集合
	 */
	private List<SetmealDish> setmealDishes;

	/**
	 * 分類名稱
	 */
	private String categoryName;
}