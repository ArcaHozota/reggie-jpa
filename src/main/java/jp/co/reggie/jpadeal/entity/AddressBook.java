package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 地址簿實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "address_book")
public class AddressBook implements Serializable {

	private static final long serialVersionUID = 3548464562522747007L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 用戸ID
	 */
	@Column(nullable = false)
	private Long userId;

	/**
	 * 簽收人
	 */
	@Column(nullable = false)
	private String consignee;

	/**
	 * 性別
	 */
	@Column(nullable = false)
	private String gender;

	/**
	 * 手機號
	 */
	@Column(name = "PHONE_NUMBER", nullable = false)
	private String phoneNo;

	/**
	 * 省級行政區劃
	 */
	private String provinceCode;

	/**
	 * 省級名稱
	 */
	private String provinceName;

	/**
	 * 市縣級行政區劃
	 */
	private String cityCode;

	/**
	 * 市縣級名稱
	 */
	private String cityName;

	/**
	 * 區級行政區劃
	 */
	private String districtCode;

	/**
	 * 區級名稱
	 */
	private String districtName;

	/**
	 * 詳細地址
	 */
	private String detail;

	/**
	 * 備注
	 */
	private String label;

	/**
	 * 是否默認
	 */
	@Column(nullable = false)
	private Integer isDefault;

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
