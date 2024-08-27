package com.paytonkawa.product_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.paytonkawa.product_service.dto.UpdateProductStockDto;

@Configuration
public class KafkaConsumerConfig {
	@Value("${kafka.topic}")
	private String kafkaTopic;
	@Bean
	public NewTopic topic() {
		return TopicBuilder.name(kafkaTopic).build();
	}
	
	public Map<String,Object> ConsumerConfiguration(){
		Map<String,Object> config = new HashMap<String,Object>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,org.springframework.kafka.support.serializer.JsonDeserializer.class);
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
		return config;
	}
	

	@Bean
	public ConsumerFactory<String, UpdateProductStockDto> consumerFactory(){
		  return new DefaultKafkaConsumerFactory<>(ConsumerConfiguration(), new StringDeserializer(),
		            new JsonDeserializer<>(UpdateProductStockDto.class, false));
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UpdateProductStockDto> kafkaListnerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String,UpdateProductStockDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
