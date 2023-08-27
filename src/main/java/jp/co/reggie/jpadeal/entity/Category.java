package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分類管理實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
public class Category extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -5583580956537498025L;

	/**
	 * 類型：1、菜品分類；2、套餐分類；
	 */
	private Integer type;

	/**
	 * 分類名稱
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 順序
	 */
	@Column(nullable = false)
	private Integer sort;

	/**
	 * 邏輯刪除字段
	 */
	@Column(nullable = false)
	private String logicDeleteFlg;
}
