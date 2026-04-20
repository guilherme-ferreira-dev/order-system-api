package com.gui.order.system.service;

import com.gui.order.system.dto.OrderRequestDTO;
import com.gui.order.system.dto.OrderResponseDTO;
import com.gui.order.system.entity.Order;
import com.gui.order.system.enums.Status;
import com.gui.order.system.exception.OrderNotFoundException;
import com.gui.order.system.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {


    private final OrderRepository repository;
    private final RabbitTemplate rabbitTemplate;


    public OrderService(RabbitTemplate rabbitTemplate, OrderRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    public OrderResponseDTO create(OrderRequestDTO dto) {
        Order order = Order.builder()
                .description(dto.getDescription())
                .userId(dto.getUserId())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = repository.save(order);

        rabbitTemplate.convertAndSend("order.queue", saved.getId());

        return OrderResponseDTO.builder()
                .id(saved.getId())
                .description(saved.getDescription())
                .status(saved.getStatus().name())
                .userId(saved.getUserId())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    private OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .description(order.getDescription())
                .status(order.getStatus().name())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public List<OrderResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public OrderResponseDTO findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return toDTO(order);
    }

    public OrderResponseDTO update(Long id, OrderRequestDTO dto) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        order.setDescription(dto.getDescription());
        order.setUserId(dto.getUserId());

        Order updated = repository.save(order);

        return toDTO(updated);
    }

    public void delete (Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        repository.delete(order);
    }

}
