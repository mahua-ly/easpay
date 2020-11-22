package com.kalo.easpay.utils.generator.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:      Panguaxe
 * @Date:        2019-11-28 23:35
 * @Description: TODO           MybatisGenerator逆向工程
 */
@Slf4j
public class MybatisGenerator {

	public static void main(String[] args) {
        log.info("==================================================== Code generation begin ====================================================");
        List<String> warnings = new ArrayList<>();
        File configFile = new File(MybatisGenerator.class.getResource("/mybatis-generator.xml").getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        try {
            Configuration config = cp.parseConfiguration(configFile);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, new DefaultShellCallback(true), warnings);
            myBatisGenerator.generate(null);
            log.info("==================================================== Code generation finish ====================================================");
        } catch (IOException | XMLParserException | InvalidConfigurationException | SQLException | InterruptedException e) {
            log.error("Mybatis Generator Exception：{}",e.getMessage());
        }
    }    
}
