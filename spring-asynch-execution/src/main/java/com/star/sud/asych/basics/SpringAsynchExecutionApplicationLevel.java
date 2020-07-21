package com.star.sud.asych.basics;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class SpringAsynchExecutionApplicationLevel implements CommandLineRunner, AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringAsynchExecutionApplicationLevel.class, args).close();
	}

	@Override
	public void run(String... args) throws Exception {

		new SpringAsynchExecutionApplicationLevel().asyncMethodWithVoidReturnTypeAppLevel();
	}

	// Sample 4: Override the Executor at the Application Level
	@Override
	public Executor getAsyncExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CustomAsyncExceptionHandler();
	}

	@Async
	public void asyncMethodWithVoidReturnTypeAppLevel() {
		System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
	}

}
