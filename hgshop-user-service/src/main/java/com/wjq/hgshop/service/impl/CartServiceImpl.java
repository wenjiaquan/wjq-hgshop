package com.wjq.hgshop.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.dao.CartDao;
import com.wjq.hgshop.dao.OrderDao;
import com.wjq.hgshop.pojo.Cart;
import com.wjq.hgshop.pojo.Order;
import com.wjq.hgshop.pojo.OrderDetail;
import com.wjq.hgshop.service.CartService;

/**
 * 
 * @author wjq
 *
 */
@Service(interfaceClass=CartService.class)
public class CartServiceImpl  implements CartService {

	@Autowired
	CartDao cartDao;
	
	@Autowired
	OrderDao orderDao;

	@Override
	public int addCart(Integer uid, int skuId, int buyNum) {
		// TODO Auto-generated method stub
		// 增加一个库存数量的一个判断
		Cart cart = new Cart(uid,skuId,buyNum);
		return cartDao.add(cart);
		
	}

	@Override
	public int delCart(int[] ids) {
		// TODO Auto-generated method stub
		return cartDao.delete(ids);
	}

	@Override
	public int clearCart(int uid) {
		// TODO Auto-generated method stub
		return cartDao.clear(uid);
	}

	@Override
	public PageInfo<Cart> list(int uid, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 5);
		
		return new PageInfo(cartDao.list(uid));
	}

	@Override
	public int createOrder(Integer uid, String address,int[] cartIds) {
		// TODO Auto-generated method stub
		//先根据购物车id生成主表的数据
		//根据购物车id 查询商品的信息
		List<Cart> cartList  = cartDao.listByIds(cartIds);
		
		//整个订单的价格
		
		Order order = new Order();
		int addNum = 0;
		
		//计算总价格
		for (Cart cart : cartList) {
			System.err.println(cart.getSumTotal());
			BigDecimal sumTotal = new BigDecimal(cart.getPnum());
			BigDecimal multiply = cart.getSumTotal().multiply(sumTotal);
			order.setUid(uid);
			order.setSumtotal(multiply);//计算总价格
			order.setAddress(address);//邮寄地址
		}
		
		//生成主表的数据
		
		//用户
		
		System.err.println(order);
		addNum += orderDao.add(order);
		
		//生成子表的数据
		for (Cart cart : cartList) {
			OrderDetail orderDetail = new OrderDetail(order.getOid(),cart);
			//插入子表
			addNum += orderDao.addDetail(orderDetail);
		}
		
		//从购物车中删除这些数据
		 cartDao.deleteByIds(cartIds);
		
		
		//成功影响数据的条数
		return addNum;
	}

	

}
