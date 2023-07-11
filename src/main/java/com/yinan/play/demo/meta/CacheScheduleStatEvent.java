/**
 * @(#)CacheAccessStatEvent.java, 2021/3/1.
 * <p/>
 * Copyright 2021 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.meta;

import com.yinan.play.demo.meta.enums.CacheOpeTypeEnum;
import com.yinan.play.demo.meta.enums.ScheduleTypeEnum;
import com.yinan.play.demo.meta.enums.StatEventTypeEnum;
import com.yinan.play.demo.meta.enums.TriggerStageEnum;

/**
 * 类说明 缓存调度事件
 *
 * @author 何瑞青(heruiqing @ corp.netease.com)
 * @version 创建时间：2021/3/1
 */
public class CacheScheduleStatEvent extends StatEvent {

    /**
     * 调度触发阶段
     */
    private TriggerStageEnum triggerStage;

    /**
     * 触发器
     */
    private String trigger;

    /**
     * 调度类型
     */
    private ScheduleTypeEnum scheduleType;

    /**
     * 调度任务信息
     */
    private String job;

    /**
     * 调度任务耗时
     */
    private Long elapsed;

    /**
     * 调度结果
     */
    private String result;

    /**
     * 调度扩展信息
     */
    private String ext;

    public static CacheScheduleStatEventBuilder builder() {
        return new CacheScheduleStatEventBuilder();
    }

    public static class CacheScheduleStatEventBuilder extends StatEvent implements Builder<CacheScheduleStatEvent> {

        /**
         * 调度触发阶段
         */
        private TriggerStageEnum triggerStage;

        /**
         * 触发器
         */
        private String trigger;

        /**
         * 调度类型
         */
        private ScheduleTypeEnum scheduleType;

        /**
         * 调度任务信息
         */
        private String job;

        /**
         * 调度任务耗时
         */
        private Long elapsed;

        /**
         * 调度结果
         */
        private String result;

        /**
         * 调度扩展信息
         */
        private String ext;

        public CacheScheduleStatEventBuilder time(long time) {
            this.time = time;
            return this;
        }

        public CacheScheduleStatEventBuilder type(StatEventTypeEnum type) {
            this.type = type;
            return this;
        }

        public CacheScheduleStatEventBuilder ope(CacheOpeTypeEnum ope) {
            this.ope = ope;
            return this;
        }

        public CacheScheduleStatEventBuilder cacheName(String cacheName) {
            this.cacheName = cacheName;
            return this;
        }

        public CacheScheduleStatEventBuilder cacheKey(String cacheKey) {
            this.cacheKey = cacheKey;
            return this;
        }

        public CacheScheduleStatEventBuilder triggerStage(TriggerStageEnum triggerStage) {
            this.triggerStage = triggerStage;
            return this;
        }

        public CacheScheduleStatEventBuilder trigger(String trigger) {
            this.trigger = trigger;
            return this;
        }

        public CacheScheduleStatEventBuilder scheduleType(ScheduleTypeEnum scheduleType) {
            this.scheduleType = scheduleType;
            return this;
        }

        public CacheScheduleStatEventBuilder job(String job) {
            this.job = job;
            return this;
        }

        public CacheScheduleStatEventBuilder elapsed(Long elapsed) {
            this.elapsed = elapsed;
            return this;
        }

        public CacheScheduleStatEventBuilder result(String result) {
            this.result = result;
            return this;
        }

        public CacheScheduleStatEventBuilder ext(String ext) {
            this.ext = ext;
            return this;
        }

        @Override
        public CacheScheduleStatEvent build() {
            return new CacheScheduleStatEvent(this);
        }
    }

    private CacheScheduleStatEvent(CacheScheduleStatEventBuilder builder) {
        this.time = builder.time;
        this.type = builder.type;
        this.ope = builder.ope;
        this.cacheName = builder.cacheName;
        this.cacheKey = builder.cacheKey;
        this.triggerStage = builder.triggerStage;
        this.trigger = builder.trigger;
        this.scheduleType = builder.scheduleType;
        this.job = builder.job;
        this.elapsed = builder.elapsed;
        this.result = builder.result;
        this.ext = builder.ext;

    }

    public TriggerStageEnum getTriggerStage() {
        return triggerStage;
    }

    public void setTriggerStage(TriggerStageEnum triggerStage) {
        this.triggerStage = triggerStage;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public ScheduleTypeEnum getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleTypeEnum scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getElapsed() {
        return elapsed;
    }

    public void setElapsed(Long elapsed) {
        this.elapsed = elapsed;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
