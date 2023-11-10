package jp.co.reggie.jpadeal.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.reggie.jpadeal.entity.Orders;
import jp.co.reggie.jpadeal.service.OrdersService;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.Reggie;

/**
 * 注文コントローラ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@RestController
@RequestMapping("/ordersDetail")
public class OrdersController {

	/**
	 * 訂單服務接口
	 */
	@Resource
	private OrdersService ordersService;

	/**
	 * 訂單信息分頁查詢
	 *
	 * @param pageNum   頁碼
	 * @param pageSize  頁面大小
	 * @param orderId   訂單ID
	 * @param beginTime 開始時間
	 * @param endTime   截止時間
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Pagination<Orders>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize,
			@RequestParam(value = "number", required = false) final Long orderId,
			@RequestParam(value = "beginTime", required = false) final String beginTime,
			@RequestParam(value = "endTime", required = false) final String endTime) {
		if ((beginTime != null) && (endTime != null)) {
			final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			final LocalDateTime time01 = LocalDateTime.parse(beginTime, timeFormatter);
			final LocalDateTime time02 = LocalDateTime.parse(endTime, timeFormatter);
			return Reggie.success(this.ordersService.pagination(pageNum, pageSize, orderId, time01, time02));
		}
		return Reggie.success(this.ordersService.pagination(pageNum, pageSize, orderId, null, null));
	}
}
