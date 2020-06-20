package com.fb2020.Store.manager;

import com.fb2020.Store.entity.Order;
import org.springframework.stereotype.Component;

@Component
public interface Manager {
    void manage(Order order);
}
