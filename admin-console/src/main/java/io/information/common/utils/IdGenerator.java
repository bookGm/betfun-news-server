package io.information.common.utils;


import com.baomidou.mybatisplus.core.toolkit.Sequence;
import io.information.config.IdWorkerConfig;

import java.util.Date;
import java.util.UUID;


/**
 * 
 * Description: 全局ID生成器 <br>
 * 
 * @Copyright: Copyright (c)2018 by Zhuzhao <br>
 *
 */
public class IdGenerator {
	private static class IdWorkerHolder {
		private static final Sequence worker = new Sequence(IdWorkerConfig.WORKER_ID, IdWorkerConfig.DATA_CENTER_ID);
	}

	private IdGenerator() {

	}

	public static final Sequence getInstance() {
		return IdWorkerHolder.worker;
	}

	/**
	 * 生成全局唯一ID
	 * @return
	 */
    public static long getId() {
        return getInstance().nextId();
    }
    
	/**
	 * 生成带指定前缀的全局唯一ID
	 * @return
	 */
    public static String getIdWithPrefix(String prefix) {
        return prefix + getInstance().nextId();
    }
    
    public static String getIdWithDatePrefix(Date date) {
    	String prefix = "";
        return prefix + getInstance().nextId();
    }

    /**
     * 生成UUID
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

	public static void main(String[] args) {
		System.out.println((IdGenerator.getId()+"").length());
	}
}
