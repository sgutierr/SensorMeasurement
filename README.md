# Sensor Measurement Project


This project reads measurements from AMQ Broker queues (insidesensor,outsidesensor and insideactuator) sending them to these topics:insidesensor,outsidesensor and insideactuator  in a Kafka cluster.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Truststore configuration to access Kafka Bootstrap url

This is only for connecting remotely a Kafka cluster deployed on OpenShift

```
oc get secret event-broker-dr-cluster-ca-cert -o yaml -o custom-columns=":.data.ca\.crt" | base64 -d | openssl x509 > ca.crt 
keytool -import -trustcacerts -alias root -file ca.crt -keystore truststore.jks -storepass password -noprompt
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.


## Testing Camel routes

Example of message to the Inside Sensor queue
{
    "temperature": 23.20,
    "humidity": 43.70,
    "co2": 400,
    "smoke": 0
 }

Example of message to the Outside Sensor queue
{
    "temperature": 23.20,
    "humidity": 43.70,
    "pressure": 1000,
    "uv": 1,
    "pm10": 30
 }

Example of message to the Inside Actuator queue
{
    "battery": 100,
    "enable": 1,
    "temperature": 25
 }


## Possible improvements

1-Serialize the message with the class instead of String and enable the Apicurio service registry integration.
2-Send mesaurements to only one Kafka topic. 

