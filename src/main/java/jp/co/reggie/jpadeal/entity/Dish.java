package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 菜品實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dish")
@NamedQuery(name = "Dish.countByCategoryId", query = "select count(1) from Dish dh where dh.logicDeleteFlg = 'visible' and dh.categoryId =:categoryId")
@NamedQuery(name = "Dish.countStatusByIds", query = "select count(1) from Dish dh where dh.logicDeleteFlg = 'visible' and dh.status = 1 and dh.id in(:ids)")
@NamedQuery(name = "Dish.findByCategoryId", query = "select dh from Dish dh where dh.logicDeleteFlg = 'visible' and dh.categoryId =:categoryId")
@NamedQuery(name = "Dish.batchRemoveByIds", query = "update Dish dh set dh.logicDeleteFlg = 'removed' where dh.id in(:dishIds)")
public final class Dish implements Serializable {

	private static final long serialVersionUID = 6089472680388107154L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 菜品名稱
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 菜品分類ID
	 */
	@Column(nullable = false)
	private Long categoryId;

	/**
	 * 菜品價格
	 */
	private BigDecimal price;

	/**
	 * 商品碼
	 */
	@Column(nullable = false)
	private String code;

	/**
	 * 圖片
	 */
	@Column(nullable = false)
	private String image;

	/**
	 * 描述信息
	 */
	private String description;

	/**
	 * 菜品售賣狀態：1:在售, 0:停售;
	 */
	@Column(nullable = false)
	private Integer status;

	/**
	 * 順序
	 */
	@Column(nullable = false)
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
	 * 邏輯刪除字段
	 */
	@Column(name = "is_deleted", nullable = false)
	private String logicDeleteFlg;

	/**
	 * 菜品口味關聯
	 */
	@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
	private List<DishFlavor> dishFlavors;
}
