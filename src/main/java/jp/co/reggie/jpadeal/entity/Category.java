package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

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
@NamedQuery(name = "Category.selectByType", query = "select ca from Category ca where ca.logicDeleteFlg = 'visible' and ca.type =:type")
@NamedQuery(name = "Category.removeById", query = "update Category ca set ca.logicDeleteFlg = 'removed' where ca.id =:id")
public class Category implements Serializable {

	private static final long serialVersionUID = -5583580956537498025L;

	/**
	 * ID
	 */
	@Id
	private Long id;

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
	 * 創建時間
	 */
	@Column(nullable = false)
	private LocalDateTime creationTime;

	/**
	 * 更新時間
	 */
	@Column(nullable = false)
	private LocalDateTime updatingTime;

	/**
	 * 創建人
	 */
	@Column(name = "creation_user", nullable = false)
	private Long createdUser;

	/**
	 * 修改者
	 */
	@Column(name = "updating_user", nullable = false)
	private Long updatedUser;

	/**
	 * 邏輯刪除字段
	 */
	@Column(nullable = false)
	private String logicDeleteFlg;
}
