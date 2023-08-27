package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 套餐實體類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Setmeal extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 4020217756505140488L;

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
	private String status;

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
	 * 邏輯刪除字段
	 */
	private String logicDeleteFlg;
}