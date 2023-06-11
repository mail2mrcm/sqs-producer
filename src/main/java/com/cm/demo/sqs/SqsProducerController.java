/**
 * 
 */
package com.cm.demo.sqs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @author KRITIENT
 *
 */
@Slf4j
@RestController
@RequestMapping("/sqs")
public class SqsProducerController {

	private static final Logger logger = LogManager.getLogger(SqsProducerController.class);
	@Autowired
	private AmazonSQS amazonSQSClient;
	
	@PostMapping("/send")
	public void sendMessage(@RequestParam("text") String text) {
		try {
			GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl("my-simple-queue");
			logger.info("Reading SQS Queue done: URL {}", queueUrl.getQueueUrl());
			amazonSQSClient.sendMessage(queueUrl.getQueueUrl(), text);
			
		} catch (Exception e) {
			logger.error("Queue does not exist {}", e.getMessage());		}
	}

}
