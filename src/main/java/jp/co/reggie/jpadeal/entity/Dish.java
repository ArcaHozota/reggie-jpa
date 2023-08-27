package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜品實體類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dish extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 6089472680388107154L;

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
	private String status;

	/**
	 * 順序
	 */
	private Integer sort;

	/**
	 * 邏輯刪除字段
	 */
	private String logicDeleteFlg;
}
