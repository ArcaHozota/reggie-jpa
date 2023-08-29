package jp.co.reggie.jpadeal.repository;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.Employee;

/**
 * 員工數據接口
 *
 * @author Administrator
 * @date 2022-11-28
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	/**
	 * 根據所提供的用戸名進行查詢
	 *
	 * @param userName 用戸名
	 * @return Employee
	 */
	Employee selectByUserName(@Param("username") String userName);

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return Employee
	 */
	Employee selectById(@Param("id") Long id);

	/**
	 * 保存新增員工
	 *
	 * @param employee 實體類對象
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void saveById(Employee employee);

	/**
	 * 根據ID修改員工信息
	 *
	 * @param employee 實體類對象
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void updateById(Employee employee);
}
