package com.zhaoli.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 消费者记录消费日志
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-15
 */
@Data
@TableName("pay_consumer_log")
//@ApiModel(value = "PayConsumerLog对象", description = "")
public class PayConsumerLog implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private String messageId;

    private String relationId;

    private String remarks;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public PayConsumerLog( String messageId, String relationId) {
        this.messageId = messageId;
        this.relationId = relationId;
    }
    public PayConsumerLog() {
    }
}
