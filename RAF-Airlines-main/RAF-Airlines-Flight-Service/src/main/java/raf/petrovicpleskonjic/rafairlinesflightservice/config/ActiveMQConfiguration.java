package raf.petrovicpleskonjic.rafairlinesflightservice.config;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfiguration {


	@Value("${activemq.broker-url}")
	public String brokerUrl;

	@Bean
	public Queue flightDeletedUserQueue() {
		return new ActiveMQQueue("flight-deleted.user.queue");
	}
	
	@Bean
	public Queue flightDeletedTicketQueue() {
		return new ActiveMQQueue("flight-deleted.ticket.queue");
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL(brokerUrl);
		return factory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(activeMQConnectionFactory());
	}
}
