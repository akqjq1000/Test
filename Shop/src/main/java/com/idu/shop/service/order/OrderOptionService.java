package com.idu.shop.service.order;

import com.idu.shop.domain.order.OrderOption;
import com.idu.shop.repository.order.OrderOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderOptionService {
    OrderOptionRepository orderOptionRepository;
    @Autowired
    public OrderOptionService(OrderOptionRepository orderOptionRepository) {
        this.orderOptionRepository = orderOptionRepository;
    }

    public List<OrderOption> readList() {return orderOptionRepository.readList();}
    public List<OrderOption> readByOrderNo(int orderNo) {return orderOptionRepository.readByOrderNo(orderNo);}
    public int insert(OrderOption orderOption) {return orderOptionRepository.insert(orderOption);}
}
