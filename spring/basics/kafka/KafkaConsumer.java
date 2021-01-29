package com.ing.api.customercontactsmg.kafka;

import com.ing.eventbus.avro.catalog.mb.auditing.CustomerContactsEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.topic}")
    public void consumeKafkaEvent(ConsumerRecord<String, CustomerContactsEvent> record) {
        
        CustomerContactsEvent customerContactsEvent = record.value();

        log.info("CustomerContactsEventListener --> " +
                "customerContactsEvent data :{} ", customerContactsEvent);
    }

       

}
