package jp.co.reggie.jpadeal.repository;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.CategoryEx;

/**
 * 分類EXリポジトリ
 *
 * @author Administrator
 * @since 3.20
 */
@Repository
public interface CategoryExRepository extends JpaRepository<CategoryEx, Long>, JpaSpecificationExecutor<CategoryEx> {

	/**
	 * リフレッシュ
	 *
	 * @param id 分類ID
	 */
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	@Query(name = "refresh materialized view category_extend", nativeQuery = true)
	void refresh();
}