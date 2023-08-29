package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.SetmealDish;

/**
 * 套餐與菜品關係數據接口
 *
 * @author Administrator
 * @since 2022-11-29
 */
public interface SetmealDishRepository extends JpaRepository<SetmealDish, Long>, JpaSpecificationExecutor<SetmealDish> {

    /**
     * 根據套餐ID查詢
     *
     * @param id 套餐ID
     * @return List<SetmealDish>
     */
    List<SetmealDish> selectBySmId(@Param("smId") Long id);

    /**
     * 根據套餐ID集合批量刪除數據
     *
     * @param ids 套餐ID集合
     */
    @Transactional(rollbackFor = PSQLException.class)
    void batchRemoveBySmIds(@Param("smIds") List<Long> ids);

    /**
     * 根據套餐集合批量更新數據
     *
     * @param setmealDishes 套餐集合
     */
    @Transactional(rollbackFor = PSQLException.class)
    void batchUpdateBySmIds(@Param("dishes") List<SetmealDish> setmealDishes);
}
