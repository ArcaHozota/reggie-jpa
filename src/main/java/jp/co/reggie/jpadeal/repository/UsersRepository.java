package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.reggie.jpadeal.entity.User;

/**
 * 客戶信息數據接口
 *
 * @author Administrator
 * @date 2022-11-29
 */
public interface UsersRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
