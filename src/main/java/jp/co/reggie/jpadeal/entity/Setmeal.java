package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 套餐實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "setmeal")
@Proxy(lazy = false)
@NamedQuery(name = "Setmeal.countByCategoryId", query = "select count(1) from Setmeal as stl where stl.deleteFlg = 'visible' and stl.categoryId =:categoryId")
@NamedQuery(name = "Setmeal.countStatusByIds", query = "select count(1) from Setmeal as stl where stl.deleteFlg = 'visible' and stl.status = 1 and stl.id in(:ids)")
@NamedQuery(name = "Setmeal.batchRemoveByIds", query = "update Setmeal as stl set stl.deleteFlg ='removed' where stl.id in(:ids)")
@NamedQuery(name = "Setmeal.batchUpdateByIds", query = "update Setmeal as stl set stl.status =:status, stl.updatedTime =:updatedTime, stl.updatedUser =:updatedUser where stl.id in(:ids)")
public final class Setmeal implements Serializable {

	private static final long serialVersionUID = 4020217756505140488L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 分類ID
	 */
	@Column(nullable = false)
	private Long categoryId;

	/**
	 * 套餐名稱
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 套餐價格
	 */
	@Column(nullable = false)
	private BigDecimal price;

	/**
	 * 套餐售賣狀態：1:在售, 0:停售;
	 */
	private Integer status;

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
	@Column(nullable = false)
	private String deleteFlg;
}