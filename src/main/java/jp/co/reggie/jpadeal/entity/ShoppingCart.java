package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 購物車實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1618550099529253148L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 名稱
	 */
	private String name;

	/**
	 * 用戸ID
	 */
	@Column(nullable = false)
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
	 * 口味ID
	 */
	private String dishFlavorId;

	/**
	 * 數量
	 */
	@Column(nullable = false)
	private Integer number;

	/**
	 * 金額
	 */
	@Column(nullable = false)
	private BigDecimal amount;

	/**
	 * 圖片
	 */
	private String image;

	/**
	 * 創建時間
	 */
	private LocalDateTime creationTime;
}
