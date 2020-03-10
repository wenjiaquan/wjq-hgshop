package com.wjq.hgshop.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.dao.BrandDao;
import com.wjq.hgshop.pojo.Brand;
import com.wjq.hgshop.service.BrandService;

@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl implements BrandService {
	@Autowired
	private BrandDao brandDao;

	@Override
	public PageInfo<Brand> list(String name, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 3);
		PageInfo<Brand> pageInfo = new PageInfo<Brand>(brandDao.list(name));
		return pageInfo;
	}

	@Override
	public int add(Brand b) {
		// TODO Auto-generated method stub
		return brandDao.add(b);
	}

	@Override
	public Brand doUpdate(Integer id) {
		// TODO Auto-generated method stub
		return brandDao.doUpdate(id);
	}

	@Override
	public int update(Brand b) {
		// TODO Auto-generated method stub
		return brandDao.update(b);
	}

	@Override
	public int delBrand(String ids) {
		// TODO Auto-generated method stub
		return brandDao.delBrand(ids);
	}
	
	
}
