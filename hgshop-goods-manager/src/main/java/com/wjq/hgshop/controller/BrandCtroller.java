package com.wjq.hgshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.pojo.Brand;
import com.wjq.hgshop.service.BrandService;

/**
 * 品牌管理
 * @author wjq
 *
 */
@Controller
@RequestMapping("brand")
public class BrandCtroller {
	
	@Reference
	private BrandService brandService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="") String name) {
		PageInfo<Brand> pageInfo = brandService.list(name, page);
		 pageInfo.setPrePage(page-1);
		 pageInfo.setNextPage(page+1);
		 request.setAttribute("pageInfo", pageInfo);
		 request.setAttribute("queryName", name);
		return "brand/list";
	}
	//品牌添加
	@ResponseBody
	@RequestMapping("add")
	public String add(Brand b) {
		int rs=brandService.add(b);
		return rs>0?"success":"falid";
	}
	/**
	 * 修改回显
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("doUpdate")
	public Object doUpdate(Integer id){
		Brand brand=brandService.doUpdate(id);
		return brand;
	}
	/**
	 * 修改品牌
	 * @param b
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String update(Brand b) {
		int rs=brandService.update(b);
		return rs>0?"success":"falid";
	}
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("delBrand")
	public String delBrand(String ids) {
		System.err.println(ids);
		int rs=brandService.delBrand(ids);
		return rs>0?"success":"falid";
	}
}
