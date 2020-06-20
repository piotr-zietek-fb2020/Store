package com.fb2020.Store.manager;

import com.fb2020.Store.entity.Invoice;
import com.fb2020.Store.entity.InvoiceDetails;
import com.fb2020.Store.entity.Order;
import com.fb2020.Store.entity.OrderDetails;
import com.fb2020.Store.service.InvoiceDetailsService;
import com.fb2020.Store.service.InvoiceService;
import com.fb2020.Store.service.OrderDetailsService;
import com.fb2020.Store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class InvoiceManager implements Manager{
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailsService orderDetailsService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    InvoiceDetailsService invoiceDetailsService;

    @Override
    public void manage(Order order) {
        Long orderId = order.getId();
        Optional<Invoice> invoiceOptional = invoiceService.getInvoiceByOrderId(orderId);
        List<OrderDetails> orderDetails = orderDetailsService.getAllByOrderId(orderId);
        if(orderDetails.size() == 0) {
            if(invoiceOptional.isPresent()) {
                invoiceService.delete(invoiceOptional.get());
            }
        } else {
            Invoice invoice;
            if(!invoiceOptional.isPresent()) {
                invoice = new Invoice(order);
            } else {
                invoice = invoiceOptional.get();
            }
            invoiceDetailsService.deleteAll();

            InvoiceDetails invoiceDetails;
            String name;
            int quantity;
            BigDecimal amount;
            for (OrderDetails detail : orderDetails) {
                name = detail.getProduct().getName();
                quantity = detail.getQuantity();
                amount = detail.getProduct().getPrice().multiply(new BigDecimal(quantity));
                invoiceDetails = new InvoiceDetails(name, quantity, amount, invoice);
                invoiceDetailsService.createInvoiceDetails(invoiceDetails);
            }
        }
    }
}
