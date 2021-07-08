package slktop.rabbit.tutorials;

public interface RabbitConstant {


    String DEMO_EX = "demo.exchange";
    String DEMO_QUEUE = "demo.queue";
    String DEMO_KEY = "demo.key";
    String DEMO_EX_TYPE = "fanout";



    // topic交换机名称
    String MQ_TOPIC_EXCHANGE_NAME = "spring_boot_topic_exchange";
    // message_key
    String MQ_EMAIL_MESSAGE_KEY = "message.email";
    String MQ_SMS_MESSAGE_KEY = "message.sms";
    String MQ_SMS_EMAIL_MESSAGE_KEY = "message.sms.email";
    // binding_key
    String MQ_SMS_BINDING_KEY = "message.#.sms.#";
    String MQ_EMAIL_BINDING_KEY = "message.#.email.#";




    // ====================================================
    String CONFIRM_EX = "confirm.exchange";
    String CONFIRM_TYPE = "fanout";

    String CONFIRM_QUEUE = "confirm.queue";
    String CONFIRM_KEY = "confirm.key";




}
