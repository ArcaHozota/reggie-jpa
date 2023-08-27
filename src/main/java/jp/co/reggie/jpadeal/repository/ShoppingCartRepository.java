package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.reggie.jpadeal.entity.ShoppingCart;

/**
 * 購物車數據接口
 *
 * @author Administrator
 * @date 2022-11-29
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
