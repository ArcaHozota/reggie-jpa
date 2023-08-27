package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.newdeal.entity.Orders;
import jp.co.reggie.newdeal.mapper.OrdersMapper;
import jp.co.reggie.newdeal.service.OrdersService;
import jp.co.reggie.newdeal.utils.Pagination;

/**
 * 訂單管理服務實現類
 *
 * @author Administrator
 * @since 2022-11-29
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

	/**
	 * 訂單數據接口
	 */
	@Resource
	private OrdersRepository ordersMapper;

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
	@Override
	public Pagination<Orders> pagination(final Integer pageNum, final Integer pageSize, final Long orderId,
			final LocalDateTime beginTime, final LocalDateTime endTime) {
		final Integer offset = (pageNum - 1) * pageSize;
		final Integer orderInfosCnt = this.ordersMapper.getOrderInfosCnt(orderId, beginTime, endTime);
		if (orderInfosCnt == 0) {
			return Pagination.of(new ArrayList<>(), orderInfosCnt, pageNum, pageSize);
		}
		final List<Orders> orderInfos = this.ordersMapper.getOrderInfos(pageSize, offset, orderId, beginTime, endTime);
		return Pagination.of(orderInfos, orderInfosCnt, pageNum, pageSize);
	}
}
