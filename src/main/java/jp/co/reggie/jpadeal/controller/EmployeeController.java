package jp.co.reggie.jpadeal.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomMessages;
import jp.co.reggie.jpadeal.dto.EmployeeDto;
import jp.co.reggie.jpadeal.entity.Employee;
import jp.co.reggie.jpadeal.service.EmployeeService;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.Reggie;
import lombok.extern.log4j.Log4j2;

/**
 * 員工管理控制器
 *
 * @author Administrator
 */
@Log4j2
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	/**
	 * 員工管理服務接口
	 */
	@Resource
	private EmployeeService employeeService;

	/**
	 * 員工登錄
	 *
	 * @param request  請求
	 * @param employee 員工信息對象
	 * @return R.success(實體類對象)
	 */
	@PostMapping("/login")
	public Reggie<EmployeeDto> login(final HttpServletRequest request, @RequestBody final EmployeeDto employeeDto) {
		// 進行登錄操作；
		final Employee aEmployee = this.employeeService.login(employeeDto);
		// 登錄成功，將員工ID存入Session並返回登錄成功；
		request.getSession().setAttribute(Constants.getEntityName(aEmployee), aEmployee.getId());
		BeanUtils.copyProperties(aEmployee, employeeDto);
		employeeDto.setName(aEmployee.getKanjiName());
		return Reggie.success(employeeDto);
	}

	/**
	 * 員工退出登錄
	 *
	 * @param request 請求
	 * @return R.success(退出登錄的信息)
	 */
	@PostMapping("/logout")
	public Reggie<String> logout(final HttpServletRequest request) {
		// 清除Session中保存的當前登錄員工的ID；
		request.getSession().removeAttribute("employee");
		return Reggie.success(CustomMessages.SRP007);
	}

	/**
	 * 保存新增員工
	 *
	 * @param employee 實體類對象
	 * @return R.success(成功增加員工的信息)
	 */
	@PostMapping
	public Reggie<String> save(@RequestBody final EmployeeDto employeeDto) {
		log.info("員工信息：{}", employeeDto.toString());
		// 保存員工信息；
		this.employeeService.save(employeeDto);
		return Reggie.success(CustomMessages.SRP006);
	}

	/**
	 * 員工信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param keyword  檢索文
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Pagination<EmployeeDto>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize,
			@RequestParam(name = "name", required = false) final String keyword) {
		// 執行查詢；
		final Pagination<EmployeeDto> pageInfo = this.employeeService.pagination(pageNum, pageSize, keyword);
		return Reggie.success(pageInfo);
	}

	/**
	 * 根據ID修改員工信息
	 *
	 * @param employee 實體類對象
	 * @return R.success(成功修改員工的信息)
	 */
	@PutMapping
	public Reggie<String> update(@RequestBody final EmployeeDto employeeDto) {
		this.employeeService.update(employeeDto);
		return Reggie.success(CustomMessages.SRP008);
	}

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return R.success(查詢到的員工的信息)
	 */
	@GetMapping("/{id}")
	public Reggie<Employee> getById(@PathVariable final Long id) {
		log.info("根據ID查詢員工信息...");
		final Employee employee = this.employeeService.getById(id);
		// 如果沒有相對應的結果，則返回錯誤信息；
		if (employee == null) {
			return Reggie.error(Constants.NO_CONSEQUENCE);
		}
		return Reggie.success(employee);
	}
}
