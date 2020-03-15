package com.wjq.hgshop.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.dao.BrandDao;
import com.wjq.hgshop.dao.CategoryDao;
import com.wjq.hgshop.dao.SkuDao;
import com.wjq.hgshop.dao.SpuDao;
import com.wjq.hgshop.pojo.Brand;
import com.wjq.hgshop.pojo.Category;
import com.wjq.hgshop.pojo.Sku;
import com.wjq.hgshop.pojo.SpecOption;
import com.wjq.hgshop.pojo.Spu;
import com.wjq.hgshop.pojo.SpuEsVo;
import com.wjq.hgshop.pojo.SpuVo;
import com.wjq.hgshop.service.GoodsService;
import com.wjq.hgshop.utils.ElSearchUtil;

/**
 * 
 * @author wjq
 *
 */
@Service(interfaceClass=GoodsService.class)
public class GoodsServiceImpl  implements GoodsService{
	
	@Autowired
	CategoryDao catDao;
	@Autowired
	SpuDao spuDao;
	@Autowired
	SkuDao skuDao;
	@Autowired
	BrandDao brandDao;
	
	@Autowired
	private ElSearchUtil<SpuEsVo> elSearchUtil;
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@Override
	public int addBrand(Brand brand) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public int updateBrand(Brand brand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBrand(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<Brand> listBrand(String firstChar, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addCategory(Category category) {
		// TODO Auto-generated method stub
		return catDao.add(category);
	}
 
	@Override
	public int updateCategory(Category category) {
		// TODO Auto-generated method stub
		return catDao.update(category);
	}

	@Override
	public int deleteCategory(Integer id) {
		// TODO Auto-generated method stub
		return catDao.delete(id);
	}

	/**
	 * 
	 */
	@Override
	public PageInfo<Category> listCategory(String firstChar, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> treeCategory() {
		// TODO Auto-generated method stub
		return catDao.tree();
	}
	
	
	// spu的列表
		@Override
		public PageInfo<Spu> listSpu(int page, SpuVo vo) {
			// TODO Auto-generated method stub
			PageHelper.startPage(page, 8);
			
			return new PageInfo<Spu>(spuDao.list(vo));
		}

		@Override
		public int addSpu(Spu spu) {
			// TODO Auto-generated method stub
			int rs=spuDao.add(spu);
			Spu newSpu= spuDao.findById(spu.getId());
			SpuEsVo spuEsVo = new SpuEsVo(newSpu);
			spuEsVo.setSmallPic(newSpu.getSmallPic());
			elSearchUtil.saveObject(spu.getId().toString(), spuEsVo);
			kafkaTemplate.send("MyAddSpu","addspu",spu.getId().toString());
			return rs;
		}

		@Override
		public int updateSpu(Spu spu) {
			// TODO Auto-generated method stub
			return spuDao.update(spu);
		}

		@Override
		public int deleteSpu(int id) {
			// TODO Auto-generated method stub
			return spuDao.delete(id);
		}

		@Override
		public int deleteSpuBatch(int[] ids) {
			// TODO Auto-generated method stub
			return spuDao.deleteSpuBatch(ids);
		}

		@Override
		public List<Brand> getAllBrands() {
			// TODO Auto-generated method stub
			return brandDao.listAll();
		}
		
		
		
		@Override
		public PageInfo<Sku> listSku(int page, Sku sku) {
			// TODO Auto-generated method stub
			PageHelper.startPage(page, 5);
			return new PageInfo<Sku>(skuDao.list(sku));
		}

		@Override
		public int addSku(Sku sku) {
			// TODO Auto-generated method stub
			//先加主表
			BigDecimal price2 = sku.getPrice();
			BigDecimal price=new BigDecimal("300");
			BigDecimal subtract = price2.subtract(price);
			sku.setCostPrice(subtract);
			sku.setCreateTime(new Date());
			sku.setUpdateTime(new Date());
			
			int cnt = skuDao.addSku(sku);
			List<SpecOption> specs = sku.getSpecs();
			for (SpecOption specOption : specs) {
				cnt += skuDao.addSkuSpec(sku.getId(),specOption);
			}
			
			return cnt;
		}

		@Override
		public Sku getSku(int id) {
			// TODO Auto-generated method stub
			return skuDao.get(id);
		}

		@Override
		public int updateSku(Sku sku) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int deleteSku(int id) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int deleteSkuBatch(int[] id) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Spu getSpu(int id) {
			// TODO Auto-generated method stub
			return spuDao.findById(id);
		}

		@Override
		public List<Sku> listSkuBySpu(int spuId) {
			// TODO Auto-generated method stub
			return skuDao.listBySpu(spuId);
		}
}
