package com.wjq.hgshop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wjq.hgshop.pojo.Brand;

public interface BrandDao {

	List<Brand> list(@Param("name") String name);

	int add(Brand b);

	Brand doUpdate(Integer id);

	int update(Brand b);

	int delBrand(@Param("ids") String ids);
	
	
	/**
	 * 获取所有的品牌
	 * @return
	 */
	@Select("SELECT id,name,first_char as firstChar "
			+ " FROM hg_brand "
			+ "where deleted_flag=0"
			+ " ORDER BY name ")
	List<Brand> listAll();
	
	List<Brand> findById(int brand_id);
}
