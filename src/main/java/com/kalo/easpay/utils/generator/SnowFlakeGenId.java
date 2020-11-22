package com.kalo.easpay.utils.generator;

import tk.mybatis.mapper.genid.GenId;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 14:28:49
 */
public class SnowFlakeGenId implements GenId<Long> {

    @Override
    public Long genId(String table, String column) {
        return SnowFlakeID.workerId();
    }
}
