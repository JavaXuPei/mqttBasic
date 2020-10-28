package com.mqtt.mqttbasis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author bjy
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName(value = "connection_config")
public class ConnectionConfigEntity extends Model<ConnectionConfigEntity> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("host")
    private String host;

    @TableField("prot")
    private String prot;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;
    @TableField("topic")
    private String topic;
    @TableField("qos")
    private String qos;

    @TableLogic
    @TableField("is_delete")
    private Integer isDelete;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
