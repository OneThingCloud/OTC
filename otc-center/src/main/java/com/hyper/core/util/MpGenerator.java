package com.hyper.core.util;


import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/** 
 * @ClassName  MpGenerator
 * @author  泡面和尚
 * @date  2017年12月21日 下午12:42:05
 */
public class MpGenerator {
	static String pageTitleBase = "用户";
	
    public static void main(String[] ars){
        String packageName = "com.hyper.web.wallet";
        generateByTables(packageName, new String[] { "t_wallet" });
    }

    private static void generateByTables(String packageName, String... tableNames){
    	AutoGenerator mpg = new AutoGenerator();
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/otc?characterEncoding=utf8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("pass")
                .setDriverName("com.mysql.jdbc.Driver")
                .setTypeConvert(new MySqlTypeConvert(){
		            // 自定义数据库表字段类型转换【可选】
		            public DbColumnType processTypeConvert(String fieldType) {
		                //System.out.println("转换类型：" + fieldType);
		                return super.processTypeConvert(fieldType);
		            }
		        });
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix(new String[] { "t_", "sys_" })
                .setSuperControllerClass("com.hyper.core.util.BaseController")
                .setInclude(tableNames); //要生成的表
        config.setActiveRecord(false)
		        .setEnableCache(false)
		        .setBaseResultMap(true)
		        .setBaseColumnList(true)
		        .setIdType(IdType.ID_WORKER)
                .setAuthor("泡面和尚")
                .setOutputDir("D://HyperSpace//OTC//otc-center//src//main//java")
                .setFileOverride(true);
        
        mpg.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("domain")
                                .setMapper("mapper")
                                .setXml("mapper")
                );
        mpg.execute();
    }
    

}
