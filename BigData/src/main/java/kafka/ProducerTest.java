package kafka;

import kafka.producer.KeyedMessage;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import java.util.Properties;
import java.util.UUID;

 /**
  * kafka生产者测试类
  * （server.properties需要配置advertised.listeners=PLAINTEXT://192.168.247.129:9092，配置listeners之后就不可默认的localhost了，得写192.168.247.129）
  * 消费者查看./bin/kafka-console-consumer.sh --bootstrap-server 192.168.247.129:9092 --topic test --from-beginning
  * @author zjh
  * @version v1.0
  * @date 2019/3/20
  */
public class ProducerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("serializer.class","kafka.serializer.StringEncoder");
        properties.put("metadata.broker.list","192.168.247.129:9092");
        properties.put("request.required.acks","1");
        ProducerConfig producerConfig = new ProducerConfig(properties);
        Producer<String,String> producer = new Producer(producerConfig);
        String topic = "test";
        int num = 100;
        for (int i = 0; i < num ; i++) {
            producer.send(new KeyedMessage<String,String>(topic,i+"" , UUID.randomUUID().toString()));
        }
        System.out.println("kafka生产者数据发送完毕...");
    }
}
