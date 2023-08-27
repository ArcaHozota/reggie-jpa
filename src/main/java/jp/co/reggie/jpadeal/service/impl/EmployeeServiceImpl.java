package jp.co.reggie.jpadeal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomException;
import jp.co.reggie.jpadeal.entity.Employee;
import jp.co.reggie.jpadeal.repository.EmployeeRepository;
import jp.co.reggie.jpadeal.service.EmployeeService;
import jp.co.reggie.jpadeal.utils.BasicContextUtils;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.StringUtils;

/**
 * 員工管理服務實現類
 *
 * @author Administrator
 * @since 2022-11-09
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	/**
	 * 員工數據接口
	 */
	@Resource
	private EmployeeRepository employeeRepository;

	/**
	 * 根據所提供的用戸名進行登錄
	 *
	 * @param employee 用戸實體類
	 * @return Employee
	 */
	@Override
	public Employee login(final Employee employee) {
		// 將頁面提交的密碼進行MD5加密；
		final String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()).toUpperCase();
		// 根據頁面提交的用戸名查詢數據庫；
		final Employee aEmployee = this.employeeRepository.selectByUserName(employee.getUsername());
		// 如果沒有查詢到或者密碼錯誤則返回登錄失敗；
		if (aEmployee == null || StringUtils.isNotEqual(password, aEmployee.getPassword())) {
			throw new CustomException(Constants.LOGIN_FAILED);
		}
		// 查看用戸狀態，如果已被禁用，則返回賬號已禁用；
		if (aEmployee.getStatus() == 0) {
			throw new CustomException(Constants.FORBIDDEN);
		}
		return aEmployee;
	}

	/**
	 * 保存新增員工
	 *
	 * @param employee 實體類對象
	 */
	@Override
	public void save(final Employee employee) {
		// 設置初始密碼，需進行MD5加密；
		final String password = DigestUtils.md5DigestAsHex(Constants.PRIMARY_CODE.getBytes()).toUpperCase();
		employee.setPassword(password);
		BasicContextUtils.fillWithInsert(employee);
		this.employeeRepository.saveById(employee);
	}

	/**
	 * 修改員工信息
	 *
	 * @param employee 實體類對象
	 */
	@Override
	public void update(final Employee employee) {
		BasicContextUtils.fillWithUpdate(employee);
		this.employeeRepository.updateById(employee);
	}

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return Employee
	 */
	@Override
	public Employee getById(final Long id) {
		return this.employeeRepository.selectById(id);
	}

	/**
	 * 員工信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param keyword  檢索文
	 * @return Pagination<Employee>
	 */
	@Override
	public Pagination<Employee> pagination(final Integer pageNum, final Integer pageSize, final String keyword) {
		final Integer offset = (pageNum - 1) * pageSize;
		final Integer employeeInfosCnt = this.employeeRepository.getEmployeeInfosCnt(keyword);
		if (employeeInfosCnt == 0) {
			return Pagination.of(new ArrayList<>(), employeeInfosCnt, pageNum, pageSize);
		}
		final List<Employee> employeeInfos = this.employeeRepository.getEmployeeInfos(pageSize, offset, keyword);
		return Pagination.of(employeeInfos, employeeInfosCnt, pageNum, pageSize);
	}
}
