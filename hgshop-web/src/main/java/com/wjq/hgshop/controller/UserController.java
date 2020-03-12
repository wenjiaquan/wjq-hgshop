package com.wjq.hgshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.comm.HgShopConstant;
import com.wjq.hgshop.pojo.Cart;
import com.wjq.hgshop.pojo.Order;
import com.wjq.hgshop.pojo.User;
import com.wjq.hgshop.service.CartService;
import com.wjq.hgshop.service.OrderService;
import com.wjq.hgshop.service.WebUserService;

/**
 * 用于处理与用户相关的请求
 * @author wjq
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	
	
	
	@Reference
	WebUserService userService;
	
	@Reference
	CartService cartService;
	
	@Reference
	OrderService orderService;

	
	
	/**
	 * 进入登陆的页面
	 * @return
	 */
	@RequestMapping("toLogin")
	public String toLogin() {
		return "user/login";
	}
	
	/**
	 * 接收用户登录的数据
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request,
			String username,String password) {
		// 登录
		User user = userService.login(username, password);
		if(user == null) {
			// 保存页面数据
			///  。。。。  todo
			request.setAttribute("error", "用户名密码错误");
			return "user/login";
		}
		// 写入session
		request.getSession().setAttribute(HgShopConstant.USEKEY, user);
		return "redirect:/user/home";
	}
	
	@RequestMapping("home")
	public String home() {
		return "/user/index";
	}
	
	/**
	 * 进入注册的页面
	 * @return
	 */
	@RequestMapping("toRegister")
	public String toRegister() {
		return "user/register";
	}
	
	/**
	 * 接收用户注册提交的数据
	 * @param request
	 * @return
	 */
	@RequestMapping("register")
	public String login(HttpServletRequest request,User user) {
		// 注册
		User registerUser = userService.register(user);
		if(registerUser == null) {
			request.setAttribute("error", "注册失败");
			return "user/register";
		}
		//注册成功 跳转到登陆页面
		return "redirect:toLogin";
	}
	
	/**
	 * 加入购物车
	 * @param skuId  
	 * @param buyNum 购买数量
	 * @return
	 */
	@RequestMapping("addCart")
	@ResponseBody
	public String addCart(HttpServletRequest request,
			int skuId, int buyNum) {
		
		//获取当前登录的用户
		User loginUser = (User)request.getSession().getAttribute(HgShopConstant.USEKEY);
		if(loginUser==null) {
			return "亲，您尚未登录，不能加入购物车哦";
		}
		
		int result = cartService.addCart(loginUser.getUid(),skuId,buyNum);
		return result>0?"success":"添加失败";
	}
	
	/**
	 * 进入个人中心
	 * @param request
	 * @return
	 */
	public String home(HttpServletRequest request) {
		return "user/home";
		
	}
	
	/**
	 * 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("cartlist")
	public String cartlist(HttpServletRequest request,
			@RequestParam(defaultValue="1") int page) {
		//获取当前登录的用户
		User loginUser = (User)request.getSession().getAttribute(HgShopConstant.USEKEY);
		if(loginUser==null) {
			request.setAttribute("error", "您尚未登陆");
			return "error";
		}
		PageInfo<Cart> cartList = cartService.list(loginUser.getUid(), page);
		cartList.setPrePage(page-1);
		cartList.setNextPage(page+1);
		request.setAttribute("pageInfo", cartList);
		return "user/cartlist";
		
	}
	
	/**
	 * 
	 * @param request
	 * @param cartIds 
	 * @param address  邮寄地址
	 * @return
	 */
	@RequestMapping("addorder")
	@ResponseBody
	public String addorder(HttpServletRequest request,
			@RequestParam("cartIds[]") int[] cartIds,String address){
		//获取当前登录的用户
		User loginUser = (User)request.getSession().getAttribute(HgShopConstant.USEKEY);
		if(loginUser==null) {
			request.setAttribute("error", "您尚未登陆");
			return "error";
		}
		System.out.println("cartIds is " + cartIds);
		int result = cartService.createOrder(loginUser.getUid(),address, cartIds);
		return result>0?"success":"添加失败";
	}
	
	/**
	 * 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("orderlist")
	public String orderlist(HttpServletRequest request,
			@RequestParam(defaultValue="1") int page) {
		//获取当前登录的用户
		User loginUser = (User)request.getSession().getAttribute(HgShopConstant.USEKEY);
		if(loginUser==null) {
			request.setAttribute("error", "您尚未登陆");
			return "error";
		}
		PageInfo<Order> list = orderService.list(loginUser.getUid(), page);
		request.setAttribute("pageInfo", list);
		return "user/orderlist";
		
	}
}
