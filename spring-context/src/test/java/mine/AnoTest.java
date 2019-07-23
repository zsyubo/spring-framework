package mine;

import mine.config.MySpringConfig;
import mine.entity.UserEntity;

public class AnoTest {

	public static void main(String[] args) {
		org.springframework.context.annotation.AnnotationConfigApplicationContext annotationConfigApplicationContext
				= new org.springframework.context.annotation.AnnotationConfigApplicationContext( MySpringConfig.class );
		System.out.println("懒汉，恶汉？");

		UserEntity userEntity = annotationConfigApplicationContext.getBean("userEntity", UserEntity.class);
		System.out.println("UserEntity: "+userEntity.toString());
	}
}
