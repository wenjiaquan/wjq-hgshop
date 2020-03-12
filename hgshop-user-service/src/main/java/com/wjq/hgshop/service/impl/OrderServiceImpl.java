package com.wjq.hgshop.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.dao.OrderDao;
import com.wjq.hgshop.pojo.Order;
import com.wjq.hgshop.pojo.OrderDetail;
import com.wjq.hgshop.service.OrderService;

/**
 * 
 * @author wjq
 *
 */
@Service(interfaceClass=OrderService.class)
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderDao orderDao;

	@Override
	public PageInfo<Order> list(int userId, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 10);
		
		return new PageInfo<Order>(orderDao.list(userId));
	}

	@Override
	public List<OrderDetail> listDetail(int orderId, int page) {
		// TODO Auto-generated method stub
		return orderDao.listDetail(orderId);
	}
	
}
