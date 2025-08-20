import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleKafkaProducer {
    public static void main(String[] args) {
        // Kafka configuration settings
        String bootstrapServers = "localhost:9092"; // Replace with your Kafka broker address
        String topicName = "my-topic"; // Replace with your topic name

        // Set the producer properties
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create a Kafka producer
        Producer<String, String> producer = new KafkaProducer<>(properties);

        try {
            // Send a message to the Kafka topic
            for (int i = 0; i < 10; i++) {
                String key = "key-" + i;
                String value = "message-" + i;
                ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);
                producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        System.out.println("Sent message: " + value + " to partition: " + metadata.partition() + " with offset: " + metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                });
            }
        } finally {
            // Close the producer
            producer.close();
        }
    }
}
