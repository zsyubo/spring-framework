package mine.config;

import mine.entity.UserEntity;
import org.springframework.context.annotation.*;

/**
 * @author 蚂蚁课堂创始人-余胜军QQ644064779
 * @title: MySpringConfig
 * @description: 每特教育独创第五期互联网架构课程
 * @date 2019/6/2221:10
 */
@Configuration
@ComponentScan(value = "mine")
//  { , excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class)}, useDefaultFilters = true
public class MySpringConfig {
    // includeFilters 包含的扫包范围 必须有这些 排除这些没有
    //@Configuration 等于 spring xml
    // <bean class="UserEntity" id="方法名称" >
    //  将该包下 的 只要有类上加上@service 注解的 注入到spring容器
    @Bean
    public UserEntity userEntity() {
        return new UserEntity("mayikt", 21);
    }
    /**
     *  记住一点 如果需要将外部的jar包注入spring容器中 @Bean
     */
}
