package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.reggie.jpadeal.entity.Orders;
import jp.co.reggie.jpadeal.repository.OrdersRepository;
import jp.co.reggie.jpadeal.service.OrdersService;
import jp.co.reggie.jpadeal.utils.Pagination;

/**
 * 訂單管理服務實現類
 *
 * @author Administrator
 * @since 2022-11-29
 */
@Service
public class OrdersServiceImpl implements OrdersService {

	/**
	 * 訂單數據接口
	 */
	@Resource
	private OrdersRepository ordersRepository;

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
		final Orders orders = new Orders();
		orders.setId(orderId);
		final Specification<Orders> whereSpecification = (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("ordersTime"), beginTime, endTime);
		final Specification<Orders> where = Specification.where(whereSpecification);
		this.ordersRepository.findAll(where);
		return Pagination.of(orderInfos, orderInfosCnt, pageNum, pageSize);
	}
}
