package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地址簿實體類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressBook extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 3548464562522747007L;

	/**
	 * 用戸ID
	 */
	private Long userId;

	/**
	 * 簽收人
	 */
	private String consignee;

	/**
	 * 手機號
	 */
	private String phoneNo;

	/**
	 * 性別
	 */
	private String gender;

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
	private Integer isDefault;

	/**
	 * 邏輯刪除字段
	 */
	private String logicDeleteFlg;
}
