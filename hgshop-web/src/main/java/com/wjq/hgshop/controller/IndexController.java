package com.wjq.hgshop.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.comm.HgShopConstant;
import com.wjq.hgshop.pojo.Category;
import com.wjq.hgshop.pojo.Sku;
import com.wjq.hgshop.pojo.Spu;
import com.wjq.hgshop.pojo.SpuEsVo;
import com.wjq.hgshop.pojo.SpuVo;
import com.wjq.hgshop.service.GoodsService;
import com.wjq.hgshop.utils.ElSearchUtil;

/**
 * 首页
 * @author wjq
 * 
 */
@Controller
public class IndexController {
	
	@Reference
	GoodsService goodsService;
	
	@Autowired
	private RedisTemplate<String, PageInfo<Spu>> redisTemplate;
	@Autowired
	private ElSearchUtil<SpuEsVo> elSearchUtil;
	/**
	 * 
	 * @param request
	 * @param page
	 * @param catId
	 * @return
	 */
	@RequestMapping({"/","index"})
	public String index(HttpServletRequest request,
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="0") int catId) {
		// 获取商品的数据
		ValueOperations<String, PageInfo<Spu>> opsForValue = redisTemplate.opsForValue();
		if(page==1&&catId==0) {
			if(redisTemplate.hasKey(HgShopConstant.SpuKEY)) {
				PageInfo<Spu> pageInfo = opsForValue.get(HgShopConstant.SpuKEY);
				request.setAttribute("pageInfo", pageInfo);
				return "index";
			}else {
				PageInfo<Spu> pageInfo = goodsService.listSpu(page, new SpuVo());
				opsForValue.set(HgShopConstant.SpuKEY,pageInfo,30,TimeUnit.MINUTES);
				request.setAttribute("pageInfo", pageInfo);
				return "index";
			}
		}
		PageInfo<Spu> pageInfo = goodsService.listSpu(page, new SpuVo());
		request.setAttribute("pageInfo", pageInfo);
		return "index";
	}
	
	/**
	 * 显示商品详情
	 * @param request
	 * @param spuId
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int spuId) {
		/**
		 * 获取详情
		 */
		// spu
		Spu spu = goodsService.getSpu(spuId);
		//sku 
		List<Sku> skuList = goodsService.listSkuBySpu(spuId);
		
		request.setAttribute("spu", spu);
		request.setAttribute("skus", skuList);
		System.out.println("skuList is " + skuList);
		return "detail";
	}
	
	@RequestMapping("treeData")
	@ResponseBody
	public List<Category> treeData(HttpServletRequest request) {
		
		return goodsService.treeCategory();
	}
	
	@RequestMapping("index/search")
	public String search(HttpServletRequest request,String key,@RequestParam(defaultValue="1") int page) {
		AggregatedPage<SpuEsVo> pageInfo = elSearchUtil.queryObjects(SpuEsVo.class, page, 10, new String[]{"goodsName","caption","brandName","categoryName"},key );
		request.setAttribute("pageInfo", pageInfo);
		return "search";
	}
}
