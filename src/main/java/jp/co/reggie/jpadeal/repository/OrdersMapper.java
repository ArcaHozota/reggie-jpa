package jp.co.reggie.jpadeal.repository;

import jp.co.reggie.newdeal.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 訂單數據接口
 *
 * @author Administrator
 * @date 2023-02-18
 */
@Mapper
public interface OrdersMapper {

	/**
	 * 訂單信息分頁查詢
	 *
	 * @param pageSize  頁面大小
	 * @param offset    偏移量
	 * @param orderId   訂單ID
	 * @param beginTime 開始時間
	 * @param endTime   截止時間
	 * @return List<Orders>
	 */
	List<Orders> getOrderInfos(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset,
			@Param("id") Long orderId, @Param("beginTime") LocalDateTime beginTime,
			@Param("endTime") LocalDateTime endTime);

	/**
	 * 檢索套餐信息總記錄數
	 *
	 * @param orderId   訂單ID
	 * @param beginTime 開始時間
	 * @param endTime   截止時間
	 * @return Integer 符合條件的總記錄數
	 */
	Integer getOrderInfosCnt(@Param("id") Long orderId, @Param("beginTime") LocalDateTime beginTime,
			@Param("endTime") LocalDateTime endTime);
}
