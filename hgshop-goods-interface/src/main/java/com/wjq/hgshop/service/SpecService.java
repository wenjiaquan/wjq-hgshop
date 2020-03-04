package com.wjq.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.pojo.Spec;

/**
 * 
 * @author wjq
 *
 */
public interface SpecService {
	
	PageInfo<Spec> list(String name,int page);
	
	int add(Spec spec);
	
	int update(Spec spec);
	
	int delete(int id);
	
	/**
	 * 查找一个规格 用于修改时候的回显
	 * @param id
	 * @return
	 */
	Spec findById(int id);
	
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	int deleteBatch(int[] id);
	
	
	
	
	
	
	
}
