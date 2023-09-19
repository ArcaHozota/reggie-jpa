package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.Category;

/**
 * 分類リポジトリ
 *
 * @author Administrator
 * @since 2022-11-19
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

	/**
	 * 分類タイプによってデータを検索する
	 *
	 * @param categoryType タイプ
	 * @return List<Category>
	 */
	List<Category> selectByType(@Param("type") Integer categoryType);

	/**
	 * 分類を削除する
	 *
	 * @param id 分類ID
	 */
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	void removeById(@Param("id") Long id);
}
