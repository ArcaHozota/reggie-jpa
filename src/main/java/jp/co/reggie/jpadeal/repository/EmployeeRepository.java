package jp.co.reggie.jpadeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.reggie.jpadeal.entity.Employee;

/**
 * 社員リポジトリ
 *
 * @author Administrator
 * @since 2022-11-28
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	/**
	 * 根據所提供的用戸名進行查詢
	 *
	 * @param userName 用戸名
	 * @return Employee
	 */
	Employee selectByUserName(@Param("username") String userName);
}
