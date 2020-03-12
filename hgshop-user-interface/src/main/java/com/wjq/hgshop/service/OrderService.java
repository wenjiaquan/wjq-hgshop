package com.wjq.hgshop.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.pojo.Order;
import com.wjq.hgshop.pojo.OrderDetail;

/**
 * 订单的服务
 * @author wjq
 *
 */
public interface OrderService {

	
	/**
	 * 查看订单
	 * @param userId
	 * @param page
	 * @return
	 */
	PageInfo<Order> list(int userId,int page);
	
	/**
	 * 查看订单详情
	 * @param orderId
	 * @param page
	 * @return
	 */
	List<OrderDetail> listDetail(int orderId,int page);
	
	
	
	

}
