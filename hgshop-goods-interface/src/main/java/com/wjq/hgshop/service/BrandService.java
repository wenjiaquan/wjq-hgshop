package com.wjq.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.pojo.Brand;

public interface BrandService {

	PageInfo<Brand> list(String name, int page);

	int add(Brand b);

	Brand doUpdate(Integer id);

	int update(Brand b);

	int delBrand(String ids);

}
