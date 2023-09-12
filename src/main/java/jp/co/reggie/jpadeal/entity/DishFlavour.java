package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 菜品口味實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "REGGIE_DISHFLAVOUR")
@NamedQuery(name = "DishFlavour.selectByDishId", query = "select af from DishFlavour af where af.logicDeleteFlg = 'visible' and af.dishId =:dishId")
@NamedQuery(name = "DishFlavour.batchRemoveByDishIds", query = "update DishFlavour af set af.logicDeleteFlg = 'removed' where af.dishId in(:dishIds)")
public class DishFlavour implements Serializable {

	private static final long serialVersionUID = 6752106293794210881L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 菜品ID
	 */
	@Column(nullable = false)
	private Long dishId;

	/**
	 * 口味名稱
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 口味數據List
	 */
	private String value;

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
	 * 邏輯刪除字段
	 */
	@Column(name = "DEL_FLG", nullable = false)
	private String logicDeleteFlg;
}
