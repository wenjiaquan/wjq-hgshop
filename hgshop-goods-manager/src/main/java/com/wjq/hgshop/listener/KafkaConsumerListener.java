
package com.wjq.hgshop.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.listener.MessageListener;

public class KafkaConsumerListener implements MessageListener<String, String>{
	
	//@Autowired	
	//private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 *  日志对象
	 */
	private Logger logger = Logger.getLogger(KafkaConsumerListener.class);
	
	{
		System.out.println("》》》》》》》》》》》》》》》》》》》》这里被实例化了。。。。。。。。。。。。。");
	}
	
	
	public void onMessage(ConsumerRecord<String, String> data) {
		
		 System.out.println("get a message .......... "  );
		//获取key值
		String key = data.key();
		
		if(key!=null) {
			 //判断
			 System.out.println("key is "  + key);
			 String value = data.value();
			 System.out.println("value is "  + value);
			 if(key.contains("addGoods")) {
				  value = data.value();
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
