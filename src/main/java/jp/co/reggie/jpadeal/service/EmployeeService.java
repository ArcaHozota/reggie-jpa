package jp.co.reggie.jpadeal.service;

import jp.co.reggie.jpadeal.dto.EmployeeDto;
import jp.co.reggie.jpadeal.entity.Employee;
import jp.co.reggie.jpadeal.utils.Pagination;

/**
 * 員工管理服務接口
 *
 * @author Administrator
 */
public interface EmployeeService {

	/**
	 * 根據所提供的用戸名進行登錄
	 *
	 * @param employeeDto 數據傳輸類對象
	 * @return Employee
	 */
	Employee login(EmployeeDto employeeDto);

	/**
	 * 保存新增員工
	 *
	 * @param employeeDto 數據傳輸類對象
	 */
	void save(EmployeeDto employeeDto);

	/**
	 * 修改員工信息
	 *
	 * @param employeeDto 數據傳輸類對象
	 */
	void update(EmployeeDto employeeDto);

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return Employee
	 */
	Employee getById(Long id);

	/**
	 * 員工信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param keyword  檢索文
	 * @return Pagination<Employee>
	 */
	Pagination<EmployeeDto> pagination(Integer pageNum, Integer pageSize, String keyword);
}
