// Authors: Liplan Lekipising and catherine Muthoni
package com.autoparts.autoparts.controllers;

import com.autoparts.autoparts.classes.OrderProduct;
import com.autoparts.autoparts.classes.Orders;
import com.autoparts.autoparts.repository.OrderProductRepository;
import com.autoparts.autoparts.services.OrderProductService;
import com.autoparts.autoparts.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.autoparts.autoparts.classes.CartCarrier.cart;

@Controller
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    OrderProductRepository orderProductRepository;

    // get all Items in Cart
    @GetMapping(path = "/allitems")
    public String getAllOrderProduct(Model model) {
        model.addAttribute("orderProducts", cart);
        return "cart";
    }

    // POST - Add an Order Product
    @RequestMapping(value = "/adding/{id}", method = RequestMethod.POST)
    public ModelAndView addToCart(@PathVariable("id") Long id,
            @ModelAttribute("orderProduct") OrderProduct orderProduct, @ModelAttribute("order") Orders order,
            BindingResult bindingResult, Model model, @RequestParam("quantity") Integer q, ModelAndView mav) {
        if (bindingResult.hasErrors()) {
            mav.addObject("errorss", "Error in adding items to cart, try again");
            mav.setViewName("errors");
        } else {
            orderProduct.setProducts(productsService.getOneProduct(id));
            orderProductService.addOrderProduct(orderProduct);
            if (q > productsService.getOneProduct(id).getCount()) {
                mav.addObject("greater", "Limit for this product is " + productsService.getOneProduct(id).getCount());
                mav.setViewName("errors");
            } else {
                cart.add(orderProduct);
                orderProduct.setProductsRemaining();
                orderProductRepository.save(orderProduct);
                mav.setViewName("redirect:/shop");
            }

        }

        return mav;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ModelAndView delProduct(Model model, @RequestParam("orderProduct") Long orderProductId) {
        ModelAndView mav = new ModelAndView("allitems");
        orderProductService.delOrderProduct(orderProductId);
        cart.clear();
        mav.setViewName("redirect:/shop");
        return mav;
    }

}
