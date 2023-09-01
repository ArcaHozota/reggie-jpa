package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.reggie.jpadeal.entity.Orders;

/**
 * 注文リポジトリ
 *
 * @author Administrator
 * @date 2023-02-18
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
}
