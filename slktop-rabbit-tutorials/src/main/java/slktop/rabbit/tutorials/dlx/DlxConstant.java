package slktop.rabbit.tutorials.dlx;

import com.rabbitmq.client.BuiltinExchangeType;

public interface DlxConstant {
    final static String DLX_NORMAL_EX = "dlx.normal";
    final static String DLX_NORMAL_QUEUE = "dlx.normal.queue";
    final static String DLX_NORMAL_KEY = "dlx.normal.key";

    final static String EX_TYPE = BuiltinExchangeType.DIRECT.getType();



    final static String DLX_DELAY_EX = "dlx.delay";
    final static String DLX_DELAY_QUEUE = "dlx.delay.queue";
    final static String DLX_DELAY_KEY = "dlx.delay.key";
}
