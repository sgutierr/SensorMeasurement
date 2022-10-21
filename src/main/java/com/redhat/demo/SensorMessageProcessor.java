package com.redhat.demo;

import java.io.IOException;

import com.redhat.demo.model.SensorMeasurement;
import org.apache.camel.Exchange;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import io.quarkus.runtime.annotations.RegisterForReflection;

@ApplicationScoped
@Named("SensorMessageProcessor")
@RegisterForReflection
public class SensorMessageProcessor {

    public SensorMessageProcessor() {
    }
    public void processRequest(Exchange exchange) throws IOException {
        SensorMeasurement obj = (SensorMeasurement) exchange.getIn().getBody();
        System.out.println(obj.getSensor());

    }
    
}
