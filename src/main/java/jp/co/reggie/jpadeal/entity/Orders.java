package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 訂單實體類
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Proxy(lazy = false)
public final class Orders implements Serializable {

	private static final long serialVersionUID = -4760386733875449380L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 訂單號
	 */
	private String number;

	/**
	 * 訂單狀態: 1待付款，2待派送，3已派送，4已完成，5已取消
	 */
	@Column(nullable = false)
	private Integer status;

	/**
	 * 客戸ID
	 */
	@Column(nullable = false)
	private Long userId;

	/**
	 * 派送地址ID
	 */
	@Column(nullable = false)
	private Long addressBookId;

	/**
	 * 訂單生成時間
	 */
	@Column(nullable = false)
	private LocalDateTime ordersTime;

	/**
	 * 付款時間
	 */
	@Column(nullable = false)
	private LocalDateTime checkoutTime;

	/**
	 * 支付方式
	 */
	@Column(nullable = false)
	private String paymentMethod;

	/**
	 * 實收金額
	 */
	@Column(nullable = false)
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
	@Column(name = "phone_num")
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
