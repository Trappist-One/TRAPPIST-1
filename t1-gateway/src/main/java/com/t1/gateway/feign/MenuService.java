package com.t1.gateway.feign;

import com.t1.common.constant.ServiceNameConstants;
import com.t1.common.entity.Menu;
import com.t1.gateway.feign.fallback.MenuServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Bruce Lee(copy)
 */
@FeignClient(name = ServiceNameConstants.T1_SYSTEM_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {
	/**
	 * 角色菜单列表
	 * @param roleCodes
	 */
	@GetMapping(value = "/menus/{roleCodes}")
	List<Menu> findByRoleCodes(@PathVariable("roleCodes") String roleCodes);
}
