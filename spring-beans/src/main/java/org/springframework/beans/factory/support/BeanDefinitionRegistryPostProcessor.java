/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * 我们可以利用这个提前注入一些BeanDefinitions, 这样我们可以在执行BeanFactoryPostProcessor时实现一些功能
 *
 *
 * 对标准BeanFactoryPostProcessor SPI的扩展，允许在常规BeanFactoryPostProcessor检测开始之前注册更多的Bean定义。
 * 特别是，BeanDefinitionRegistryPostProcessor可以注册更多的Bean定义，而这些定义又会定义BeanFactoryPostProcessor实例。
 *
 * Extension to the standard {@link BeanFactoryPostProcessor} SPI, allowing for
 * the registration of further bean definitions <i>before</i> regular
 * BeanFactoryPostProcessor detection kicks in. In particular,
 * BeanDefinitionRegistryPostProcessor may register further bean definitions
 * which in turn define BeanFactoryPostProcessor instances.
 *
 * @author Juergen Hoeller
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 * @since 3.0.1
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

	/**
	 * 在标准初始化之后，修改应用程序上下文的内部Bean定义注册表。所有常规的Bean定义将被加载，但还没有Bean被实例化。这允许在下一个后处理阶段启动之前进一步添加Bean定义。
	 *
	 * Modify the application context's internal bean definition registry after its
	 * standard initialization. All regular bean definitions will have been loaded,
	 * but no beans will have been instantiated yet. This allows for adding further
	 * bean definitions before the next post-processing phase kicks in.
	 * @param registry the bean definition registry used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;

}
