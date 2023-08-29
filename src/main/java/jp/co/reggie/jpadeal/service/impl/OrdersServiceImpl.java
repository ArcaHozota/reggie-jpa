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
		final PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
		Specification<Orders> whereSpecification1;
		if (beginTime != null && endTime != null) {
			whereSpecification1 = (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("ordersTime"),
					beginTime, endTime);
		} else if (beginTime == null && endTime != null) {
			whereSpecification1 = (root, query, criteriaBuilder) -> criteriaBuilder
					.lessThanOrEqualTo(root.get("ordersTime"), endTime);
		} else if (beginTime != null) {
			whereSpecification1 = (root, query, criteriaBuilder) -> criteriaBuilder
					.greaterThanOrEqualTo(root.get("ordersTime"), beginTime);
		} else {
			whereSpecification1 = null;
		}
		final Specification<Orders> whereSpecification2 = orderId == null ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), orderId.toString());
		final Specification<Orders> where = Specification.where(whereSpecification1).and(whereSpecification2);
		final Page<Orders> orders = this.ordersRepository.findAll(where, pageRequest);
		return Pagination.of(orders.getContent(), orders.getTotalElements(), pageNum, pageSize);
	}
}
