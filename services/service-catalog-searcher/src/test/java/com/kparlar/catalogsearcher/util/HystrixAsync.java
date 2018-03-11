package com.kparlar.catalogsearcher.util;

import com.kparlar.catalogsearcher.exception.CatalogSearcherException;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HystrixAsync {
    public <T> T resolve(Future<T> future) {
        return ((AsyncResult<T>) future).invoke();
    }

    public Future<List<SearchResponseDto>> getFuture(List<SearchResponseDto> searchResponseDtos){
        Future<List<SearchResponseDto>> future = new Future<List<SearchResponseDto>>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public List<SearchResponseDto> get() throws InterruptedException, ExecutionException {
                return searchResponseDtos;
            }

            @Override
            public List<SearchResponseDto> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        return  future;
    }

    public Future<List<SearchResponseDto>> getFutureForInterruptedException(){
        Future<List<SearchResponseDto>> future = new Future<List<SearchResponseDto>>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public List<SearchResponseDto> get() throws InterruptedException, ExecutionException {
                throw new InterruptedException();
            }

            @Override
            public List<SearchResponseDto> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        return  future;
    }
    public Future<List<SearchResponseDto>> getFutureForExecutionException(){
        Future<List<SearchResponseDto>> future = new Future<List<SearchResponseDto>>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public List<SearchResponseDto> get() throws InterruptedException, ExecutionException {
                throw new java.util.concurrent.ExecutionException(CatalogSearcherTestConstants.DUMMY_TEXT, new Exception());
            }

            @Override
            public List<SearchResponseDto> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        return  future;
    }

}
