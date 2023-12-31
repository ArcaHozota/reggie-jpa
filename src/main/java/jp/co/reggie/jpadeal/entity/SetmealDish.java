package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 套餐與菜品關係實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "setmeal_dish")
@NamedQuery(name = "SetmealDish.batchRemoveBySmIds", query = "delete from SetmealDish as smd where smd.setmealId in(:smIds)")
public final class SetmealDish implements Serializable {

	private static final long serialVersionUID = -641135780975738908L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 套餐ID
	 */
	@Column(nullable = false)
	private Long setmealId;

	/**
	 * 菜品ID
	 */
	@Column(nullable = false)
	private Long dishId;

	/**
	 * 菜品名稱(冗餘字段)
	 */
	private String name;

	/**
	 * 菜品原價(冗餘字段)
	 */
	private BigDecimal price;

	/**
	 * 份數
	 */
	@Column(nullable = false)
	private Integer copies;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 創建時間
	 */
	@Column(nullable = false)
	private LocalDateTime createdTime;

	/**
	 * 更新時間
	 */
	@Column(nullable = false)
	private LocalDateTime updatedTime;

	/**
	 * 創建人
	 */
	@Column(nullable = false)
	private Long createdUser;

	/**
	 * 修改者
	 */
	@Column(nullable = false)
	private Long updatedUser;

	/**
	 * 菜品套餐關聯
	 */
	@ManyToOne
	@JoinColumn(name = "setmealId", insertable = false, updatable = false)
	private Setmeal setmeal;
}
