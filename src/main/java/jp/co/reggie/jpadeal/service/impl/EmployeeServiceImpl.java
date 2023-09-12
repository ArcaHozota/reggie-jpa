package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomException;
import jp.co.reggie.jpadeal.dto.EmployeeDto;
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
	public void save(final EmployeeDto employeeDto) {
		// 設置初始密碼，需進行MD5加密；
		final Employee employee = new Employee();
		final String password = DigestUtils.md5DigestAsHex(Constants.PRIMARY_CODE.getBytes()).toUpperCase();
		BeanUtils.copyProperties(employeeDto, employee);
		employee.setId(BasicContextUtils.getGeneratedId());
		employee.setKanjiName(employeeDto.getName());
		employee.setPassword(password);
		employee.setStatus(Constants.STATUS_VALID);
		employee.setCreatedTime(LocalDateTime.now());
		employee.setUpdatedTime(LocalDateTime.now());
		employee.setCreatedUser(BasicContextUtils.getCurrentId());
		employee.setUpdatedUser(BasicContextUtils.getCurrentId());
		this.employeeRepository.save(employee);
	}

	/**
	 * 修改員工信息
	 *
	 * @param employee 實體類對象
	 */
	@Override
	public void update(final Employee employee) {
		employee.setUpdatedTime(LocalDateTime.now());
		employee.setUpdatedUser(BasicContextUtils.getCurrentId());
		this.employeeRepository.save(employee);
	}

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return Employee
	 */
	@Override
	public Employee getById(final Long id) {
		final Optional<Employee> optional = this.employeeRepository.findById(id);
		return optional.orElse(null);
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
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
		final Employee employee = new Employee();
		employee.setKanjiName(keyword);
		final ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("kanjiName",
				GenericPropertyMatchers.contains());
		final Example<Employee> example = Example.of(employee, exampleMatcher);
		final Page<Employee> employees = this.employeeRepository.findAll(example, pageRequest);
		return Pagination.of(employees.getContent(), employees.getTotalElements(), pageNum - 1, pageSize);
	}
}
