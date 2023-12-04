package com.art.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.DeliveryStatusDAO;
import com.art.dao.promotion.OrderDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.models.product.Product;
import com.art.models.promotion.DeliveryStatus;
import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDetail;
import com.art.service.ParamService;

@Controller
@RequestMapping("/admin")
public class OrderDetailController {

	@Autowired
	OrderDetailDAO orderdetailDAO;

	@Autowired
	OrderDAO odDAO;
	@Autowired
	ProductDAO productDAO;

	@Autowired
	ParamService paramService;

	@Autowired
	DeliveryStatusDAO deliveryDAO;

	@ModelAttribute("productList")
	public Map<Product, String> getproduct() {
		List<Product> products = productDAO.findAll();
		Map<Product, String> map = new HashMap<>();
		System.out.println("product LISt" + products);
		for (Product c : products) {
			map.put(c, c.getProductName());
		}
		return map;
	}

	@GetMapping("/OrderDetail/{Id}")
	public String OderDetailById(@ModelAttribute("OrderDetail") OrderDetail orderdetail, @PathVariable("Id") Integer Id,
			Model model) {
		model.addAttribute("title", "Quản lí hóa đơn chi tiết");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
//		List<Product> products=productDAO.findAll();
//		model.addAttribute("productList",products);
		Order od = odDAO.getById(Id);
		model.addAttribute("OrderDetails", orderdetailDAO.findByOrder(od));
		model.addAttribute("idOrder", Id);
		return "admin/OrderDetail-form";
	}

	@PostMapping("/OrderDetail/create")
	public String Create(@ModelAttribute("OrderDetail") OrderDetail orderdetail, BindingResult bd, Model model) {
		int OrderID = paramService.getInt("idOrder", -1);
		Order oder = odDAO.getById(OrderID);
		orderdetail.setOrder(oder);
		System.out.println("product:" + orderdetail.getProduct());

		long price = orderdetailDAO.findPriceByProductId(orderdetail.getProduct().getProductId());
		orderdetail.setPrice(price);
		orderdetailDAO.save(orderdetail);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		DeliveryStatus deliverystt = new DeliveryStatus();
		deliverystt.setStatus(false);
		deliverystt.setDate(new Date());
		deliverystt.setOrderStatus(oder);
		deliverystt.setDetailUpdate(
				" Acccount  create :" + authentication.getName() + "Create ID : " + orderdetail.getId() + " Product : "
						+ orderdetail.getProduct().getProductId() + " Quantity :" + orderdetail.getQuantity());

		deliveryDAO.save(deliverystt);

		return "redirect:/admin/OrderDetail/" + OrderID;
	}

	@RequestMapping("/OrderDetail/{orderId}/edit/{orderDetailID}")
	public String Edit(@ModelAttribute("OrderDetail") OrderDetail orderdetail, @PathVariable("orderId") int orderID,
			@PathVariable("orderDetailID") int orderetailid, BindingResult bd, Model model) {
		model.addAttribute("title", "Quản lí hóa đơn chi tiết");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("OrderDetail", orderdetailDAO.getById(orderetailid));
		model.addAttribute("idOrder", orderID);
		Order od = odDAO.getById(orderID);
		model.addAttribute("OrderDetails", orderdetailDAO.findByOrder(od));
		return "admin/OrderDetail-form";
	}

	@RequestMapping("/OrderDetail/{orderId}/update")
	public String update(@ModelAttribute("OrderDetail") OrderDetail orderdetail, @PathVariable("orderId") int orderID,
			BindingResult bd, Model model) {
		model.addAttribute("title", "Quản lí hóa đơn chi tiết");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		Order oder = odDAO.getById(orderID);
		orderdetail.setOrder(oder);
		model.addAttribute("idOrder", orderID);
		long price = orderdetailDAO.findPriceByProductId(orderdetail.getProduct().getProductId());
		orderdetail.setPrice(price);
		Order od = odDAO.getById(orderID);
		orderdetailDAO.save(orderdetail);
		model.addAttribute("OrderDetails", orderdetailDAO.findByOrder(od));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		DeliveryStatus deliverystt = new DeliveryStatus();
		deliverystt.setStatus(false);
		deliverystt.setDate(new Date());
		deliverystt.setOrderStatus(oder);
		deliverystt.setDetailUpdate(

				" Acccount  update :" + authentication.getName() + "Update ID : " + orderdetail.getId() + " Product : "
						+ orderdetail.getProduct().getProductId() + " Quantity :" + orderdetail.getQuantity());

		deliveryDAO.save(deliverystt);
		return "redirect:/admin/OrderDetail/" + orderID;
	}

	@RequestMapping("/OrderDetail/{orderId}/delete")
	@ResponseBody
	public String delete(@ModelAttribute("OrderDetail") OrderDetail orderdetail,@PathVariable("orderId") int orderID,
			BindingResult bd, Model model) {
		

		Order oder = odDAO.getById(orderID);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		DeliveryStatus deliverystt = new DeliveryStatus();
		deliverystt.setStatus(false);
		deliverystt.setDate(new Date());
		deliverystt.setOrderStatus(oder);
		deliverystt.setDetailUpdate(

				" Acccount  update :" + authentication.getName() + "Delete ID : " + orderdetail.getId() + " Product : "
						+ orderdetail.getProduct().getProductId() + " Quantity :" + orderdetail.getQuantity());

		deliveryDAO.save(deliverystt);
		orderdetailDAO.deleteById(orderdetail.getId());
		return "redirect:/admin/OrderDetail/" + orderID;
	}
}
