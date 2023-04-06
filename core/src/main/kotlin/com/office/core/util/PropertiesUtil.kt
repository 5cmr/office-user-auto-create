package com.prprpr.core.util

import org.apache.commons.configuration2.CombinedConfiguration
import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Configurations
import org.apache.commons.configuration2.tree.DefaultExpressionEngine
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols


/**
 * properties配置读取帮助类
 * @see 'https://blog.csdn.net/sdut406/article/details/88398891?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param'
 * @see 'https://cloud.tencent.com/developer/article/1011718'
 */
class PropertiesUtil {

    companion object StaticParams {
        private var config:  CombinedConfiguration

        init {
            val engine = DefaultExpressionEngine(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS)
            val configurations = Configurations()
            config  = configurations.combined("conf/root.xml")
            config.expressionEngine = engine
        }


        /**
         * 获取String类型的value
         * @param key
         * @return
         */
        @JvmStatic
        @Throws(Exception::class)
        fun getValueString(key: String): String? {
            return config.getString(key)
        }
        /**
         * 获取int类型的value
         * @param key
         * @return
         */
        @JvmStatic
        @Throws(Exception::class)
        fun getValueInt(key: String): Int {
            return config.getInt(key, 0)
        }
    }

}