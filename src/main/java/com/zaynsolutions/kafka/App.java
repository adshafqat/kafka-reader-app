package com.zaynsolutions.kafka;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        // Bootstrap servers and topic name
        String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
        String topicName = System.getenv("TOPIC_NAME");
        String groupId = System.getenv("GROUP_ID");
        
        String truststoreLocation = System.getenv("TRUSTSTORE_LOCATION");
        String truststorePassword = System.getenv("TRUSTSTORE_PASSWORD");
        String truststoreType = System.getenv("TRUSTSTORE_TYPE");

        // Validate required environment variables
        if (bootstrapServers == null || topicName == null || groupId == null) {
            System.err.println("Please provide values for BOOTSTRAP_SERVER, GROUP_ID and TOPIC_NAME environment variables.");
            return;
        }

        // Configure Kafka consumer properties
        Properties config = new Properties();
        config.put("bootstrap.servers", bootstrapServers);
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id", groupId);

        config.put("security.protocol", "SSL");
        config.put("ssl.truststore.location", truststoreLocation);
        config.put("ssl.truststore.password", truststorePassword);
        config.put("ssl.truststore.type", truststoreType);

          // Create a Kafka consumer
        KafkaConsumer<String, String> consumer = KafkaConsumer.create(Vertx.vertx(), config);

        // Subscribe to a Kafka topic
        consumer.subscribe(topicName, ar -> {
            if (ar.succeeded()) {
                System.out.println("Consumer subscribed successfully");
            } else {
                System.err.println("Failed to subscribe consumer: " + ar.cause().getMessage());
            }
        });

        // Start consuming messages
        consumer.handler(record -> {
            KafkaConsumerRecord<String, String> kafkaRecord = (KafkaConsumerRecord<String, String>) record;
            System.out.println("Received message: " + kafkaRecord.value());
        });
    }
}
