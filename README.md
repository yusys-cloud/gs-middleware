# spring-amqp

使用Spring Cloud Stream 验证优雅停机

集成

``` 
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
</dependency>
```
### graceful-shutdown

### spring-context

org.springframework.boot.web.context

- ServletWebServerApplicationContext 中使用了 WebServerGracefulShutdownLifecycle 

org.springframework.context.support

- DefaultLifecycleProcessor

- AbstractApplicationContext#doClose() 发送关闭事件(ContextClosedEvent事件的Listener实现了```Lifecycle```生命周期接口的bean)与调用lifecycleProcessor.onClose()


``` 
/**
	 * Actually performs context closing: publishes a ContextClosedEvent and
	 * destroys the singletons in the bean factory of this application context.
	 * <p>Called by both {@code close()} and a JVM shutdown hook, if any.
	 * @see org.springframework.context.event.ContextClosedEvent
	 * @see #destroyBeans()
	 * @see #close()
	 * @see #registerShutdownHook()
	 */
	@SuppressWarnings("deprecation")
	protected void doClose() {
		// Check whether an actual close attempt is necessary...
		if (this.active.get() && this.closed.compareAndSet(false, true)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Closing " + this);
			}

			if (!NativeDetector.inNativeImage()) {
				LiveBeansView.unregisterApplicationContext(this);
			}

			try {
				// Publish shutdown event.
				publishEvent(new ContextClosedEvent(this));
			}
			catch (Throwable ex) {
				logger.warn("Exception thrown from ApplicationListener handling ContextClosedEvent", ex);
			}

			// Stop all Lifecycle beans, to avoid delays during individual destruction.
			if (this.lifecycleProcessor != null) {
				try {
					this.lifecycleProcessor.onClose();
				}
				catch (Throwable ex) {
					logger.warn("Exception thrown from LifecycleProcessor on context close", ex);
				}
			}

			// Destroy all cached singletons in the context's BeanFactory.
			destroyBeans();

			// Close the state of this context itself.
			closeBeanFactory();

			// Let subclasses do some final clean-up if they wish...
			onClose();

			// Reset local application listeners to pre-refresh state.
			if (this.earlyApplicationListeners != null) {
				this.applicationListeners.clear();
				this.applicationListeners.addAll(this.earlyApplicationListeners);
			}

			// Switch to inactive.
			this.active.set(false);
		}
	}
```

### spring-cloud-stream-binder-rabbit

RabbitServiceAutoConfiguration中使用spring-amqp中CachingConnectionFactory与ConnectionFactory

RabbitTemplate使用spring-amqp的 ```ConnectionFactory```

``` 
 @Bean
                @ConditionalOnMissingBean({RabbitTemplate.class})
                RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
                    return new RabbitTemplate(connectionFactory);
                }
```

## Exchanges
https://rabbitmq.com/tutorials/amqp-concepts.html#exchanges 