package com.nineleaps.orderservice.callback;

import com.nineleaps.orderservice.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@SuppressWarnings("rawtypes")
public class CustomKafkaCallback implements ListenableFutureCallback {

    @Override
    public void onFailure(Throwable throwable) {
        //log error message on failure
        log.error("Couldn't send message to the broker: ", throwable);
        throw new ServiceException("Not able to process massage");
    }

    @Override
    public void onSuccess(Object result) {
        //on success do not do anything
        //Intentionally left black
    }
}
