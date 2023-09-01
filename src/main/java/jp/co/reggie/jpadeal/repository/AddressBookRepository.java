package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.reggie.jpadeal.entity.AddressBook;

/**
 * アドレスリポジトリ
 *
 * @author Administrator
 * @since 2022-11-08
 */
@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long>, JpaSpecificationExecutor<AddressBook> {
}
