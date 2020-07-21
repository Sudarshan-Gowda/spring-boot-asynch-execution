package com.star.sud.asych.basics;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class SpringAsynchExecutionMethodLevel implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringAsynchExecutionMethodLevel.class, args).close();
	}

	// Sample 1: Methods With Void Return Type
	// Following is the simple way to configure a method with void return type to
	// run asynchronously:
	@Async
	public void asyncMethodWithVoidReturnType() {
		System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
	}

	// Sample 2: Methods With Return Type
	// 1. @Async can also be applied to a method with return type – by wrapping the
	// actual return in the Future:
	// 2. Spring also provides an AsyncResult class which implements Future. This
	// can
	// be used to track the result of asynchronous method execution.
	@Async
	public Future<String> asyncMethodWithReturnType() {
		System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
		try {
			Thread.sleep(5000);
			return new AsyncResult<String>("hello world !!!!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void testAsyncAnnotationForMethodsWithReturnType() throws InterruptedException, ExecutionException {
		System.out.println("Invoking an asynchronous method. " + Thread.currentThread().getName());
		Future<String> future = new SpringAsynchExecutionMethodLevel().asyncMethodWithReturnType();

		while (true) {
			if (future.isDone()) {
				System.out.println("Result from asynchronous process - " + future.get());
				break;
			}
			System.out.println("Continue doing something else. ");
			Thread.sleep(1000);
		}
	}

	// Sample 3: Override the Executor at the Method Level
	@Async("threadPoolTaskExecutor")
	public void asyncMethodWithConfiguredExecutor() {
		System.out.println("Execute method with configured executor - " + Thread.currentThread().getName());
	}

	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@Override
	public void run(String... args) throws Exception {

		new SpringAsynchExecutionMethodLevel().asyncMethodWithVoidReturnType();
		new SpringAsynchExecutionMethodLevel().asyncMethodWithReturnType();
		new SpringAsynchExecutionMethodLevel().testAsyncAnnotationForMethodsWithReturnType();
		new SpringAsynchExecutionMethodLevel().asyncMethodWithConfiguredExecutor();
	}

}
