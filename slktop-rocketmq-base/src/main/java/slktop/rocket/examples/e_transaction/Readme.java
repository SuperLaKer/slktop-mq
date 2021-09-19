package slktop.rocket.examples.e_transaction;

/**
 * 事务消息
 * 事务消息共有三种状态，提交状态、回滚状态、中间状态：
 *
 * TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
 * TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
 * TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
 */
public class Readme {
}
