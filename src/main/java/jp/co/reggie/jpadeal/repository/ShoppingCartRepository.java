package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.reggie.jpadeal.entity.ShoppingCart;

/**
 * ショッピングカートリポジトリ
 *
 * @author Administrator
 * @date 2022-11-29
 */
@Repository
public interface ShoppingCartRepository
		extends JpaRepository<ShoppingCart, Long>, JpaSpecificationExecutor<ShoppingCart> {
}
