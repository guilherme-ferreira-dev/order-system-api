package com.gui.order.system.controller;

import com.gui.order.system.dto.OrderRequestDTO;
import com.gui.order.system.dto.OrderResponseDTO;
import com.gui.order.system.entity.Order;
import com.gui.order.system.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponseDTO create(@RequestBody OrderRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<OrderResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(
            @PathVariable Long id,
            @RequestBody OrderRequestDTO dto) {

        OrderResponseDTO response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
