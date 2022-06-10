package com.example.zoostore.service;

import com.example.oauth2.model.User;
import com.example.oauth2.service.UserService;
import com.example.zoostore.dto.request.CreateOrderDtoRequest;
import com.example.zoostore.dto.response.OrderDtoResponse;
import com.example.zoostore.dto.response.OrderProductDtoResponse;
import com.example.zoostore.model.*;
import com.example.zoostore.repository.OrderProductsRepository;
import com.example.zoostore.repository.OrderRepository;
import com.example.zoostore.repository.PaymentRepository;
import com.example.zoostore.utils.OrderUtils;
import com.example.zoostore.utils.model.OrderProductFacade;
import com.example.zoostore.utils.model.PaymentFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final ProductService productService;
    private final ProfileService profileService;
    private final UserService userService;


    @Transactional(rollbackFor = {RuntimeException.class})
    public HttpStatus createOrder(CreateOrderDtoRequest request) {
        final BigDecimal[] totalPrice = {new BigDecimal(0)};
        Payment payment = PaymentFacade.paymentDtoRequestToPayment(request);
        User user = userService.getUserById(profileService.getUserId());
        List<OrderProduct> orderProducts = request.getOrders().stream()
                .map(e -> {
                    OrderProduct orderProduct = OrderProductFacade.orderDtoRequestToOrderProduct(e);
                    Product product = productService.getProductById(orderProduct.getId());
                    if (orderProduct.getQuantity() > product.getQuantity()) {
                        throw new IllegalArgumentException("Product with id " + orderProduct.getId() + " is out of stock");
                    }
                    orderProduct.setProduct(product);
                    totalPrice[0] = totalPrice[0].add(BigDecimal.valueOf(product.getPrice() * orderProduct.getQuantity()));
                    return orderProduct;
                })
                .collect(Collectors.toList());
        Order order = orderRepository.save(Order.builder()
                .user(user)
                .isPaid(false)
                .total(totalPrice[0])
                .build());
        orderProducts.forEach(e -> e.setOrder(order));
        orderProductsRepository.saveAll(orderProducts);
        payment.setOrder(order);
        payment.setStatus(Status.ACTIVE);
        paymentRepository.save(payment);
        return HttpStatus.OK;
    }

    public List<OrderDtoResponse> getAllOrders() {
        Long userId = profileService.getUserId();
        return orderRepository.findAllByUserId(userId).stream()
                .map(e -> {
                    Payment payment = getPaymentByOrderId(e.getId());
                    OrderDtoResponse orderDtoResponse = new OrderDtoResponse();
                    OrderUtils.orderAndPaymentOrderDtoResponse(orderDtoResponse, e, payment);
                    orderDtoResponse.setOrderProducts(getAllOrderProductsResponse(e.getId()));
                    return orderDtoResponse;
                }).collect(Collectors.toList());
    }

    public List<OrderProductDtoResponse> getAllOrderProductsResponse(Long id) {
        return getAllOrderProducts(id).stream()
                .map(OrderProductFacade::orderProductToOrderProductDtoResponse)
                .collect(Collectors.toList());
    }

    public List<OrderProduct> getAllOrderProducts(Long id) {
        return orderProductsRepository.getAllByOrderId(id);
    }

    public Payment getPaymentByOrderId(Long id) {
        return paymentRepository.findPaymentByOrderId(id).orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public HttpStatus payForOrder(Long id) {
        Payment payment = getPaymentByOrderId(id);
        Order order = payment.getOrder();
        if (!Objects.equals(order.getUser().getId(), profileService.getUserId())) {
            throw new IllegalArgumentException("You can't pay for this order");
        }
        getAllOrderProducts(order.getId()).forEach(e -> {
            Product product = e.getProduct();
            if (product.getQuantity() < e.getQuantity()) {
                throw new IllegalArgumentException("Product with id " + product.getId() + " is out of stock");
            }
            product.setQuantity(product.getQuantity() - e.getQuantity());
            productService.updateProduct(product);
        });
        payment.setStatus(Status.DONE);
        order.setPaid(true);
        paymentRepository.save(payment);
        return HttpStatus.OK;
    }
}
