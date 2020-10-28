package com.mqtt.mqttbasis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mqtt.mqttbasis.entity.ConnectionConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author bjy
 */
@Mapper
@Repository
public interface ConnectionConfigMapper extends BaseMapper<ConnectionConfigEntity> {
}
