package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.reggie.jpadeal.entity.Orders;

/**
 * 訂單數據接口
 *
 * @author Administrator
 * @date 2023-02-18
 */
public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
}
