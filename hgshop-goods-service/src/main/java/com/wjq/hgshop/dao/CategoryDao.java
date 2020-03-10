package com.wjq.hgshop.dao;

import java.util.List;

import com.wjq.hgshop.pojo.Brand;
import com.wjq.hgshop.pojo.Category;

/**
 * 
 * @author wjq
 *
 */
public interface CategoryDao {

	 List<Category> tree();

	int add(Category category);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int delete(Integer id);

	/**
	 * 修改
	 * @param category
	 * @return
	 */
	int update(Category category);
	
	List<Category> findById(int category_id);
}
