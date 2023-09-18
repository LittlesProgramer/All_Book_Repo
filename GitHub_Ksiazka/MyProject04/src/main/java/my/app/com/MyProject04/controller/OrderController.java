package my.app.com.MyProject04.controller;

import lombok.extern.slf4j.Slf4j;
import my.app.com.MyProject04.data.OrderRepository;
import my.app.com.MyProject04.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes(names = {"order"})
public class OrderController {

    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String getOrder(Order order){
        log.info("order = "+order);
        return "orderForm";
    }

    @PostMapping
    public String postOrderAndRedirectToHome(Order order,SessionStatus sessionStatus){

        orderRepo.save(order);
        sessionStatus.setComplete();
        log.info("post order = "+order);
        return "redirect:/";
    }
}
