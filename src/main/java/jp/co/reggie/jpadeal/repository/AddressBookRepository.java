package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.reggie.jpadeal.entity.AddressBook;

/**
 * 地址簿數據接口
 *
 * @author Administrator
 * @date 2022-11-08
 */
public interface AddressBookRepository extends JpaRepository<AddressBook, Long>, JpaSpecificationExecutor<AddressBook> {
}
