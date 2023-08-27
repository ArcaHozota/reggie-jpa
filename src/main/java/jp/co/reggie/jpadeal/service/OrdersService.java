package jp.co.reggie.jpadeal.service;

import java.time.LocalDateTime;

import jp.co.reggie.jpadeal.entity.Orders;
import jp.co.reggie.jpadeal.utils.Pagination;

/**
 * 訂單管理服務接口
 *
 * @author Administrator
 */
public interface OrdersService {

	/**
	 * 訂單信息分頁查詢
	 *
	 * @param pageNum   頁碼
	 * @param pageSize  頁面大小
	 * @param orderId   訂單ID
	 * @param beginTime 開始時間
	 * @param endTime   截止時間
	 * @return Pagination<Orders>
	 */
	Pagination<Orders> pagination(Integer pageNum, Integer pageSize, Long orderId, LocalDateTime beginTime,
			LocalDateTime endTime);
}
