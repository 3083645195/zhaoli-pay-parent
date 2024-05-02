# zhaoli-pay-parent
项目概述:为商户端提供“极简支付”、“聚合支付”、“智能便携pos终端”等多套支付领域软硬件应用解决方案，实现用户支付结果及时的采用微信公众号模板通知给商户端，开发公司企业级微信公众号，统一维护管理微信公众号关键字回复、消息模板推送、菜单管理、账户和用户管理等。<br />责任描述:<br />1. 负责对接企业微信公众号开发<br />2. 微信公众号开发核心模块的开发<br />3. 提供微信公众号核心消息模板(支付结果和商家入驻审核模板)<br />4. 调用企业级微信公众号API生成二维码<br />技术描述:<br />1. 使用第三方外网映射工具调试微信公众号;<br />2. 使用wxjava开源框架开发微信公众号;<br />3. 利用httpclient调用第三方外部智能机器人接口，实现关键字回复;<br />4. 使用Redis缓存微信公众号菜单信息相关配置;<br />5. 为了提高支付异步回调接口快速响应，利用线程池将异步发送用户支付结果改造成异步的形式，<br />避免第三方支付接口不断重试，保证幂等性问题;<br />6. 项目中采用Spring提供@Async注解结合自定义线程池（ThreadPoolTaskExecutor） 实现异步处理;<br />7. 为了避免生产环境发生cpu使用率飙高问题，后期改造成基于rabbitmq方式实现异步处理;<br />8. 为了避免消费者重复发送微信公众号消息模板，利用消息全局id（支付的id）去重避免消息重复消费;<br />9. 后期随着并发量的增加，为了避免消息堆积的情况下，消费者会采用集群的方式消费，让后每个消费者批量获取msg消息，当我们消费者消费失败的情况下配置消费者消费消息间隔（3s）重试次数，利用消息全局id+redis记录该msg消息消费的异常被mq重试的次数，如果重试多次还是失败的情况下，我们消费者将该msg转移投递到死信队列中;<br />10. 后期项目中为了避免消息堆积的问题，生产者采用批量的方式投递消息到MQ中、消费者采用集群+批量的形式获取msg;<br />11. 将生产者投递的多条消息，合并成一条消息存放在mq服务器端中，消费者将该msg拆分n多个小msg实现批量消费，最终实现优化生产者（投递消息的速率）和消费者（消费速率）提高10倍<br />12. 利用aop解决整个api接口的跨域的问题


以下该内容是为简历描述详细内容：

### 第一阶段（整合企业微信公众号）

1.整合企业微信公众号 整合微信消息模板接口 实现给我们商户端发送模板消息；<br />2.利用httpclient调用第三方机器人（青云客智能）聊天接口 实现微信公众号关键字自动回复；

### 第二阶段（整合线程池）

1.为了避免支付宝发送异步回调达到聚合支付项目，利用线程池将耗时代码改造异步执行快速让我们接口响应,避免发生幂等性问题; <br />2.项目中采用Spring提供@Async注解结合自定义线程池（ThreadPoolTaskExecutor） 实现异步处理；<br />3.利用到我们线程池+Future模式 获取到异步方法的返回结果，线程池核心参数采用IO密集型设定<br />服务器的核数*2配置

### 第三阶段（rabbitmq）

1.为了避免生产环境发生cpu使用率飙高，二期改造成基于mq改造异步处理<br />2.相关采用 分层领域模型规约  （do、dto、bo）封装对象 保证参数传递安全性问题<br />3.消费者利用消息的全局id去重避免消息重复消费

### 第四阶段（消息异常处理）

1.为了避免消息堆积的情况下，消费者会采用集群的方式消费，让后每个消费者批量获取msg消息。<br />2.生产中发现消费者消费msg失败的情况下，mq服务器端会给消费者不断重试 导致 消费者没有及时获取下一条 最终引发消息积压问题。<br />3.当我们消费者消费失败的情况下配置消费者消费消息间隔（3s）重试次数，如果重试多次的情况下还是失败，则我们会将该消息转移到死信队列中，后期单独死信消费者处理该问题。<br />4.利用消息全局id+redis记录该msg消息消费的异常被mq重试的次数，如果重试多次还是失败的情况下，我们消费者将该msg转移投递到死信队列中。

### 第五阶段（批量消费）

1.后期项目中为了避免消息堆积的问题，生产者采用批量的方式投递消息到MQ中、消费者采用集群+批量的形式获取msg。<br />2.将生产者投递的多条消息，合并成一条消息 存放在mq服务器端中，消费者将该msg拆分n多个小msg。<br />3.优化生产者（投递消息的速率）和消费者（消费速率）提高5倍

### 第六阶段（商家入驻聚合支付）

1.完成开发商家入驻api接口<br />2.调用企业微信公众号API生成临时二维码有效期30天<br />2.用户扫二维码接口实现企业级微信公众号与商户端绑定openid

### 第七阶段（商家入驻前端页面-微信扫一扫）

1.调用企业级微信公众号API生成二维码，传递二维码token值<br />用户扫码成功之后，腾讯服务器会给我们开发者服务器发送<br />请求（二维码token、openid），写入redis缓存中<br />2.前端写定时器每隔2s会根据二维token查询redis是否有<br />openid，如果有的情况下 扫码成功实现页面自动跳转。<br />3.负责开发我们的商家入驻二维码接口，实现微信扫一扫自动<br />网页跳转<br />4.利用@CrossOrigin注解前后端跨域的问题<br />5.利用db中根据企业社会信用代码作为全局id，表设定唯一约束防止用户重复插入<br />6.后期利用aop（后置通知  设置api接口允许跨域问题）解决整个api接口的跨域的问题

### 第八阶段（取消关注公众号）

1.监听取消关注实现对应用户openid  变为null;

### 第九阶段（开放API接口平台设计）

1.商家入驻成功之后，会给每个商家分配一个appid和app秘钥，商户才可以调用开放api接口，appid是无法改变 但是app秘钥改变的;<br />2.利用MD5验证签名防止黑客篡改数据

MD5加密之后是否可以解密呢？单向加密<br />123（盐值-====456）---单向加密之后mmm<br />123（盐值-====456）---单向加密之后kkk

### 第十阶段 封装sdk 自定义springboot插件

1.第二期中封装我们的极简聚合支付商户端sdk spring-boot-starter插件<br />方便商户端便捷调用聚合支付api接口<br />2.商户端发送rpc请求 插件自动在请求头传递appid appkey  签名值 每个商户都有自己独立盐值 防止泄漏导致可以暴力参数内容

### 第十一阶段 支付宝 sdk 沙箱环境说明

1.对接第三方支付宝、微信支付 沙箱环境配置

### 第十二阶段 使用设计模式重构

1.利用支付令牌解决商家与聚合支付平台传递参数的安全性问题<br />2.基于策略模式封装 选择多个不同支付渠道减少多重if判断的问题

### 第十三阶段 基于策略+模板方法重构

1.使用模板方法模式定义异步回调共同的骨架<br />2.根据策略 选择不同的模板处理执行<br />3.利用mq重试策略将用户支付结果 通知给商户端

### 第十四阶段 定时任务跑批处理

1.利用xxljob 定时集群分片算法+多线程 批量的形式发送<br />实现思路：大数据跑批的时候 需要让定时任务集群的执行，<br />在每个定时中开启多线程异步执行，如果在后期数据量不断<br />增加的情况下 动态对定时服务器扩容即可。

原理：<br />1.定时任务（执行器）集群形式启动，注册到 定时任务注册中心上面。<br />2.所有定时任务触发都是在调度中心上，调度中心从注册中心获取<br />定时任务集群地址列表，在给每个定时任务发送执行任务命令传递<br />index值，就是该定时任务在注册中心列表的index位置。定时获取index值<br />查询不同段数据，在进行多线程批量处理。<br />

某个批次定时任务如果发送失败的情况下，记录日志形式继续向下发，后期单独额外补偿。<br />

### 第十五阶段 利用测试模式 同步支付状态

负责开发公司根据第三方支付id或者商户订单号码 主动调用<br />支付宝接口同步 支付状态。
