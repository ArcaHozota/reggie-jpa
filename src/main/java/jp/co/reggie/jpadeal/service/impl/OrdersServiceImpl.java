package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	private static final String ORDERS_TIME = "ordersTime";

	/**
	 * 訂單數據接口
	 */
	@Resource
	private OrdersRepository ordersRepository;

	@Override
	public Pagination<Orders> pagination(final Integer pageNum, final Integer pageSize, final Long orderId,
			final LocalDateTime beginTime, final LocalDateTime endTime) {
		final PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
		final Specification<Orders> whereSpecification1 = orderId == null ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), orderId.toString());
		final Specification<Orders> whereSpecification2 = this.getOrdersSpecification(beginTime, endTime);
		final Specification<Orders> where = Specification.where(whereSpecification1).and(whereSpecification2);
		final Page<Orders> orders = this.ordersRepository.findAll(where, pageRequest);
		return Pagination.of(orders.getContent(), orders.getTotalElements(), pageNum, pageSize);
	}

	/**
	 * 獲取起始時間與截止日期的檢索條件
	 *
	 * @param beginTime 起始時間
	 * @param endTime   截止日期
	 * @return Specification<Orders>
	 */
	private Specification<Orders> getOrdersSpecification(final LocalDateTime beginTime, final LocalDateTime endTime) {
		Specification<Orders> whereSpecification1;
		if (beginTime != null && endTime != null) {
			whereSpecification1 = (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(ORDERS_TIME),
					beginTime, endTime);
		} else if (beginTime == null && endTime != null) {
			whereSpecification1 = (root, query, criteriaBuilder) -> criteriaBuilder
					.lessThanOrEqualTo(root.get(ORDERS_TIME), endTime);
		} else if (beginTime != null) {
			whereSpecification1 = (root, query, criteriaBuilder) -> criteriaBuilder
					.greaterThanOrEqualTo(root.get(ORDERS_TIME), beginTime);
		} else {
			whereSpecification1 = null;
		}
		return whereSpecification1;
	}
}
