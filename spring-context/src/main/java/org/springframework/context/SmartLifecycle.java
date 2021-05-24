/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.context;

/**
 * 生命周期接口的一个扩展，用于那些需要在ApplicationContext刷新和/或关闭时按特定顺序启动的对象。isAutoStartup()的返回值表明该对象是否应该在上下文刷新时被启动。接受回调的stop(Runnable)方法对于有异步关机过程的对象很有用。这个接口的任何实现都必须在关机完成后调用回调的run()方法，以避免整个ApplicationContext关机过程中出现不必要的延迟。
 * 这个接口扩展了Phased，getPhase()方法的返回值表示这个生命周期组件应该在哪个阶段启动和停止。启动过程从最低的阶段值开始，以最高的阶段值结束（Integer.MIN_VALUE是可能的最低值，Integer.MAX_VALUE是可能的最高值）。关机过程将采用相反的顺序。任何具有相同价值的组件将在同一阶段内被任意排序。
 * 例如：如果组件B依赖于组件A已经启动，那么组件A的相位值应该比组件B低。在关机过程中，组件B将在组件A之前被停止。
 * 任何明确的 "依赖 "关系将优先于阶段顺序，这样，依赖的Bean总是在其依赖的对象之后开始，并且总是在其依赖的对象之前停止。
 * 上下文中任何没有实现SmartLifecycle的生命周期组件将被视为其相位值为0。这样一来，如果SmartLifecycle的相位值为负数，它可以在这些生命周期组件之前启动，如果相位值为正数，它可以在这些组件之后启动。
 * 请注意，由于SmartLifecycle支持自动启动，在任何情况下，SmartLifecycle Bean实例通常会在应用上下文的启动时被初始化。因此，bean definition lazy-init标志对SmartLifecycle Bean的实际影响非常有限。
 *
 * An extension of the {@link Lifecycle} interface for those objects that require to
 * be started upon ApplicationContext refresh and/or shutdown in a particular order.
 * The {@link #isAutoStartup()} return value indicates whether this object should
 * be started at the time of a context refresh. The callback-accepting
 * {@link #stop(Runnable)} method is useful for objects that have an asynchronous
 * shutdown process. Any implementation of this interface <i>must</i> invoke the
 * callback's {@code run()} method upon shutdown completion to avoid unnecessary
 * delays in the overall ApplicationContext shutdown.
 *
 * <p>This interface extends {@link Phased}, and the {@link #getPhase()} method's
 * return value indicates the phase within which this Lifecycle component should
 * be started and stopped. The startup process begins with the <i>lowest</i> phase
 * value and ends with the <i>highest</i> phase value ({@code Integer.MIN_VALUE}
 * is the lowest possible, and {@code Integer.MAX_VALUE} is the highest possible).
 * The shutdown process will apply the reverse order. Any components with the
 * same value will be arbitrarily ordered within the same phase.
 *
 * <p>Example: if component B depends on component A having already started,
 * then component A should have a lower phase value than component B. During
 * the shutdown process, component B would be stopped before component A.
 *
 * <p>Any explicit "depends-on" relationship will take precedence over the phase
 * order such that the dependent bean always starts after its dependency and
 * always stops before its dependency.
 *
 * <p>Any {@code Lifecycle} components within the context that do not also
 * implement {@code SmartLifecycle} will be treated as if they have a phase
 * value of 0. That way a {@code SmartLifecycle} implementation may start
 * before those {@code Lifecycle} components if it has a negative phase value,
 * or it may start after those components if it has a positive phase value.
 *
 * <p>Note that, due to the auto-startup support in {@code SmartLifecycle}, a
 * {@code SmartLifecycle} bean instance will usually get initialized on startup
 * of the application context in any case. As a consequence, the bean definition
 * lazy-init flag has very limited actual effect on {@code SmartLifecycle} beans.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @since 3.0
 * @see LifecycleProcessor
 * @see ConfigurableApplicationContext
 */
public interface SmartLifecycle extends Lifecycle, Phased {

	/**
	 * The default phase for {@code SmartLifecycle}: {@code Integer.MAX_VALUE}.
	 * <p>This is different from the common phase 0 associated with regular
	 * {@link Lifecycle} implementations, putting the typically auto-started
	 * {@code SmartLifecycle} beans into a separate later shutdown phase.
	 * @since 5.1
	 * @see #getPhase()
	 * @see org.springframework.context.support.DefaultLifecycleProcessor#getPhase(Lifecycle)
	 */
	int DEFAULT_PHASE = Integer.MAX_VALUE;


	/**
	 * Returns {@code true} if this {@code Lifecycle} component should get
	 * started automatically by the container at the time that the containing
	 * {@link ApplicationContext} gets refreshed.
	 * <p>A value of {@code false} indicates that the component is intended to
	 * be started through an explicit {@link #start()} call instead, analogous
	 * to a plain {@link Lifecycle} implementation.
	 * <p>The default implementation returns {@code true}.
	 * @see #start()
	 * @see #getPhase()
	 * @see LifecycleProcessor#onRefresh()
	 * @see ConfigurableApplicationContext#refresh()
	 */
	default boolean isAutoStartup() {
		return true;
	}

	/**
	 * Indicates that a Lifecycle component must stop if it is currently running.
	 * <p>The provided callback is used by the {@link LifecycleProcessor} to support
	 * an ordered, and potentially concurrent, shutdown of all components having a
	 * common shutdown order value. The callback <b>must</b> be executed after
	 * the {@code SmartLifecycle} component does indeed stop.
	 * <p>The {@link LifecycleProcessor} will call <i>only</i> this variant of the
	 * {@code stop} method; i.e. {@link Lifecycle#stop()} will not be called for
	 * {@code SmartLifecycle} implementations unless explicitly delegated to within
	 * the implementation of this method.
	 * <p>The default implementation delegates to {@link #stop()} and immediately
	 * triggers the given callback in the calling thread. Note that there is no
	 * synchronization between the two, so custom implementations may at least
	 * want to put the same steps within their common lifecycle monitor (if any).
	 * @see #stop()
	 * @see #getPhase()
	 */
	default void stop(Runnable callback) {
		stop();
		callback.run();
	}

	/**
	 * Return the phase that this lifecycle object is supposed to run in.
	 * <p>The default implementation returns {@link #DEFAULT_PHASE} in order to
	 * let stop callbacks execute after regular {@code Lifecycle} implementations.
	 * @see #isAutoStartup()
	 * @see #start()
	 * @see #stop(Runnable)
	 * @see org.springframework.context.support.DefaultLifecycleProcessor#getPhase(Lifecycle)
	 */
	@Override
	default int getPhase() {
		return DEFAULT_PHASE;
	}

}
