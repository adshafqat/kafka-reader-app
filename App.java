package com.example.kafka;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        // Bootstrap servers and topic name
        String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
        String topicName = System.getenv("TOPIC_NAME");

        // Validate required environment variables
        if (bootstrapServers == null || topicName == null) {
            System.err.println("Please provide values for BOOTSTRAP_SERVERS and TOPIC_NAME environment variables.");
            return;
        }

        // Configure Kafka consumer properties
        Properties config = new Properties();
        config.put("bootstrap.servers", bootstrapServers);
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id", "my-consumer-group");

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
