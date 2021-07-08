package slktop.rabbit.tutorials.a_java;

/**
 * exchange的topic模式:
 *  direct模式指定的routing_key是固定的，
 *  而topic模式指定的routing_key支持正则表达式
 * 正则规则:
 *  * 可以匹配   一个 '单词'，'单词'，'单词'
 *  # 可以匹配 >= 0个 '单词' 和 '.'
 * 如下:
 * - Q1: *.orange.*
 * - Q2: *.*.rabbit
 * - Q2: lazy.#

 * - quick.orange.rabbit            Q1 and Q2
 * - lazy.orange.elephant           only Q1
 * - quick.orange.fox               only Q1
 * - lazy.pink.rabbit               only Q2
 * - quick.brown.fox                none

 * - orange                         none
 * - quick.orange.male.rabbit       none
 * - lazy.orange.male.rabbit        Q2
 * <p>
 * 案例 通知系统：
 * 手机短信通知、邮件通知，
 * 目的：publish_key如何设计？ binding_key如何设计？
 * <p>
 * public_key这样设计:
 * pk1   send.email       仅邮件通知
 * pk2   send.sms         仅短信通知
 * pk3   send.sms.email   短信、邮件都通知
 * <p>
 * bind_key这样设计
 * bk1  send.#.email.#
 * bk2  send.#.sms.#
 * <p>
 * <p>
 *
 * topic: binding_key 仅用 一个 # 字符，相当于 fanout广播
 * topic: binging_key 不用 * 和 # , 相当于 direct非正则key
 */
public class EA_Topic {
}
