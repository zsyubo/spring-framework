package mine.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"mine/applicationContext.xml","mine/applicationContext2.xml"}
		);
		Source source = (Source) context.getBean("source");
		System.out.println(source.getFruit());
		System.out.println(source.getSugar());
		System.out.println(source.getSize());
	}
}
