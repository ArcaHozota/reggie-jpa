package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 訂單實體類
 */
@Data
public class Orders implements Serializable {

	private static final long serialVersionUID = -4760386733875449380L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 訂單號
	 */
	private String number;

	/**
	 * 訂單狀態: 1待付款，2待派送，3已派送，4已完成，5已取消
	 */
	private Integer status;

	/**
	 * 客戸ID
	 */
	private Long userId;

	/**
	 * 派送地址ID
	 */
	private Long addressBookId;

	/**
	 * 訂單生成時間
	 */
	private LocalDateTime orderTime;

	/**
	 * 付款時間
	 */
	private LocalDateTime checkoutTime;

	/**
	 * 支付方式
	 */
	private String payMethod;

	/**
	 * 實收金額
	 */
	private BigDecimal amount;

	/**
	 * 備注
	 */
	private String remark;

	/**
	 * 客戸名稱
	 */
	private String userName;

	/**
	 * 收貨人手機號
	 */
	private String phoneNo;

	/**
	 * 派送地址
	 */
	private String address;

	/**
	 * 簽收人昵稱
	 */
	private String consignee;
}
