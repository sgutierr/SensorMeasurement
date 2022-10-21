package com.redhat.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.redhat.demo.model.InsideActuatorMeasurement;
import com.redhat.demo.model.InsideSensorMeasurement;
import com.redhat.demo.model.OutsideSensorMeasurement;
public class QueueSensorConsumerRoute extends EndpointRouteBuilder {

    private Map<String, String> additionalProperties = new HashMap<>();
       

    @Override
    public void configure() throws Exception {

        // Setting additional Kafka properties like apicurio url
        additionalProperties.put("apicurio.registry.url", "http://apicurioregistry-kafkasql.appdev-apidesigner.apps.ocp4.quitala.eu/apis/registry/v2");
        
        // TODO Auto-generated method stub
        // Camel JMS component detects the ConnectionFactory
        // which is created by the Camel Quarkus AMQP extension
        from(jms("{{AMQ.queue.insidesensor}}"))
                .log("Received a message in inside sensor queue: ${body}")
                // unmashal json to pojo (based on generated code from sensor-measurement.avsc)
                .unmarshal().json(JsonLibrary.Jackson, InsideSensorMeasurement.class)
                
                // This is a test about how to add a custom Processor, it is not doing anything, just print a field.
                //.bean(SensorMessageProcessor.class, "processRequest")

                // Set the Key of the Kafka Record with the sensor name
                .setHeader(KafkaConstants.KEY, simple("InsideSensor"))
               
                // Destination a Kafka topic as endpoint, 
                // There is the additional properties with the apicurio service registry url, even it is disabled.
                // Value Serializer is a String, it could be a CustomAvroSerializer
                .to(kafka("{{kafka.topic.insidesensor}}")
                  .brokers("{{kafka.bootstrap.servers}}")
                  .securityProtocol("{{kafa.security.protocol}}")
                  .sslTruststoreLocation("{{kafka.ssl.truststore.location}}")
                  .sslTruststorePassword("{{kafka.ssl.truststore.password}}")
                  .keySerializer("{{kafka.key.serializer}}")
                  .valueSerializer("{{kafka.value.serializer}}")
                  .additionalProperties(additionalProperties)

                 )
                ;


                from(jms("{{AMQ.queue.outsidesensor}}"))
                .log("Received a message in outside sensor queue: ${body}")
                // unmashal json to pojo (based on generated code from sensor-measurement.avsc)
                .unmarshal().json(JsonLibrary.Jackson, OutsideSensorMeasurement.class)
                
                // This is a test about how to add a custom Processor, it is not doing anything, just print a field.
                //.bean(SensorMessageProcessor.class, "processRequest")

                // Set the Key of the Kafka Record with the sensor name
                .setHeader(KafkaConstants.KEY, simple("OutsideSensor"))
               
                // Destination a Kafka topic as endpoint, 
                // There is the additional properties with the apicurio service registry url, even it is disabled.
                // Value Serializer is a String, it could be a CustomAvroSerializer
                .to(kafka("{{kafka.topic.outsidesensor}}")
                  .brokers("{{kafka.bootstrap.servers}}")
                  .securityProtocol("{{kafa.security.protocol}}")
                  .sslTruststoreLocation("{{kafka.ssl.truststore.location}}")
                  .sslTruststorePassword("{{kafka.ssl.truststore.password}}")
                  .keySerializer("{{kafka.key.serializer}}")
                  .valueSerializer("{{kafka.value.serializer}}")
                  .additionalProperties(additionalProperties)

                 )
                ;                

                from(jms("{{AMQ.queue.insideactuator}}"))
                .log("Received a message in InsideActuator queue: ${body}")
                // unmashal json to pojo (based on generated code from sensor-measurement.avsc)
                .unmarshal().json(JsonLibrary.Jackson, InsideActuatorMeasurement.class)
                
                // This is a test about how to add a custom Processor, it is not doing anything, just print a field.
                //.bean(SensorMessageProcessor.class, "processRequest")

                // Set the Key of the Kafka Record with the sensor name
                .setHeader(KafkaConstants.KEY, simple("InsideActuator"))
               
                // Destination a Kafka topic as endpoint, 
                // There is the additional properties with the apicurio service registry url, even it is disabled.
                // Value Serializer is a String, it could be a CustomAvroSerializer
                .to(kafka("{{kafka.topic.insideactuator}}")
                  .brokers("{{kafka.bootstrap.servers}}")
                  .securityProtocol("{{kafa.security.protocol}}")
                  .sslTruststoreLocation("{{kafka.ssl.truststore.location}}")
                  .sslTruststorePassword("{{kafka.ssl.truststore.password}}")
                  .keySerializer("{{kafka.key.serializer}}")
                  .valueSerializer("{{kafka.value.serializer}}")
                  .additionalProperties(additionalProperties)
                 )
                ;  
    }
    
}
