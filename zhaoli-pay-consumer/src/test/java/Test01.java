import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class Test01 {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/zhaolipay?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8",
                "root", "20020806")
                .globalConfig(builder -> {
                    builder.author("zhaoli") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\java\\project\\mybatis-plus"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zhaoli.pay") // 设置父包名
                            //                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\java\\project\\mybatis-plus")); // 设置mapperXml生成路径
                })
                //                .strategyConfig(builder -> {
                //                    builder.addInclude("t_simple") // 设置需要生成的表名
                //                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                //                })
                //                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
