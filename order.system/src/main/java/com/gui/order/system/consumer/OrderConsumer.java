package com.gui.order.system.consumer;

import com.gui.order.system.entity.Order;
import com.gui.order.system.enums.Status;
import com.gui.order.system.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final OrderRepository repository;

    public OrderConsumer(OrderRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "order.queue")
    public void processOrder(Long orderId) {

        System.out.println("Processando pedido: " + orderId);

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        try {
            Thread.sleep(3000); // simula processamento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        order.setStatus(Status.PROCESSED);
        repository.save(order);

        System.out.println("Pedido processado: " + orderId);
    }
}