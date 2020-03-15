package com.wjq.hgshop.listen;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.listener.MessageListener;

import com.github.pagehelper.PageInfo;
import com.wjq.hgshop.comm.HgShopConstant;
import com.wjq.hgshop.pojo.Spu;
import org.apache.log4j.Logger;
public class KafkaConsumerListener implements MessageListener<String,String>{
	
	@Autowired
	private RedisTemplate<String, PageInfo<Spu>> redisTemplate;
	
	/**
	 *  日志对象
	 */
	private Logger logger = Logger.getLogger(KafkaConsumerListener.class);
	{
		System.out.println("》》》》》》》》》》》》》》》》》》》》这里被实例化了。。。。。。。。。。。。。");
	}
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		// TODO Auto-generated method stub
		String key = data.key();
		if(key!=null &&key.equals("addspu")) {
			String value = data.value();
			System.out.println("新添加的商品的id是"  + value );
			//清空缓存
			redisTemplate.delete(HgShopConstant.SpuKEY);
		}
	}

}
