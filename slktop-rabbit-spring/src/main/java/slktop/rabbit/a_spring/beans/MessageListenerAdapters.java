package slktop.rabbit.a_spring.beans;

import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import slktop.rabbit.a_spring.conveter.TulingImageConverter;

import java.util.HashMap;
import java.util.Map;


/**
 * 消息的内容不同就需要不同的处理方式
 */
public class MessageListenerAdapters {

    /**
     * 指定默认回调方法
     */
    public static MessageListenerAdapter defaultMethodPolicy(MessageListenerAdapter listenerAdapter) {
        listenerAdapter.setDefaultListenerMethod("callback");
        return listenerAdapter;
    }

    /**
     * 前提：listenerContainer可以监听多个队列
     * 不同的queue使用不同的方法处理接收到的消息
     */
    public static MessageListenerAdapter queueMethodMappingPolicy(MessageListenerAdapter listenerAdapter) {
        /*不同队列不同方法消费*/
        Map<String, String> queueMethodMap = new HashMap<>();
        queueMethodMap.put("queue名字", "方法名字");
        listenerAdapter.setQueueOrTagToMethodName(queueMethodMap);
        return listenerAdapter;
    }

    /**
     * 消息内容是json，消息处理方法的型参是Map类型
     */
    public static MessageListenerAdapter jsonHandlerPolicy(MessageListenerAdapter messageListenerAdapter) {
        messageListenerAdapter.setMessageConverter(new Jackson2JsonMessageConverter());
        messageListenerAdapter.setDefaultListenerMethod("方法型参是Map类型");
        return messageListenerAdapter;

    }

    /**
     * 消息内容是entity，消息处理方法的型参是entity类型
     */
    public static MessageListenerAdapter objectHandlerPolicy(MessageListenerAdapter messageListenerAdapter) {
        /*处理Java Object*/
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper defaultJackson2JavaTypeMapper = new DefaultJackson2JavaTypeMapper();
        defaultJackson2JavaTypeMapper.setTrustedPackages("xxx.xx.entity");
        jackson2JsonMessageConverter.setJavaTypeMapper(defaultJackson2JavaTypeMapper);
        messageListenerAdapter.setMessageConverter(new Jackson2JsonMessageConverter());
        messageListenerAdapter.setDefaultListenerMethod("型参是entity类型");
        return messageListenerAdapter;
    }

    public static MessageListenerAdapter imgHandlerPolicy(MessageListenerAdapter messageListenerAdapter) {
        // 消费文件
        ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter();
        messageConverter.addDelegate("img/png", new TulingImageConverter());


        messageListenerAdapter.setDefaultListenerMethod("consumerFile");
        return messageListenerAdapter;
    }
}
