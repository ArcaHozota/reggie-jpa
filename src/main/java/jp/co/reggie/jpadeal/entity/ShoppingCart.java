package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 購物車實體類
 *
 * @author Administrator
 */
@Data
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1618550099529253148L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 名稱
	 */
	private String name;

	/**
	 * 用戸ID
	 */
	private Long userId;

	/**
	 * 菜品ID
	 */
	private Long dishId;

	/**
	 * 套餐ID
	 */
	private Long setmealId;

	/**
	 * 口味
	 */
	private String dishFlavor;

	/**
	 * 數量
	 */
	private Integer number;

	/**
	 * 金額
	 */
	private BigDecimal amount;

	/**
	 * 圖片
	 */
	private String image;

	/**
	 * 創建時間
	 */
	private LocalDateTime createTime;
}
