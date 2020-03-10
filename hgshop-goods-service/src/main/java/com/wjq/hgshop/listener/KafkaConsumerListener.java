
package com.wjq.hgshop.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaConsumerListener implements MessageListener<String, String>{
	
	@Autowired

	

	
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 *  日志对象
	 */
	private Logger logger = Logger.getLogger(KafkaConsumerListener.class);
	
	
	public void onMessage(ConsumerRecord<String, String> data) {
		//获取key值
		String key = data.key();
		
		if(key!=null) {
			 //判断
			System.out.println("key is "  + key);
			 if(key.contains("addGoods")) {
				 String value = data.value();
				 try {
					// 做某些事情
					 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }else if(key.contains("")) {
				 
			 }
		}else {
			logger.info("key未null值");
		}
		
	}

}
