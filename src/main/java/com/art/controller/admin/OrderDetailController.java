package com.art.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Comment;
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
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.CommentOrderDetailDAO;
import com.art.dao.promotion.DeliveryStatusDAO;
import com.art.dao.promotion.OrderDAO;
import com.art.dao.promotion.OrderDeliveryStatusDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.models.product.Product;
import com.art.models.product.ProductDetail;
import com.art.models.promotion.DeliveryStatus;
import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDelieveryStatus;
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
	ProductDetailDAO productdetailDAO;

	@Autowired
	ParamService paramService;

	@Autowired
	OrderDeliveryStatusDAO orderdeliveryDAO;

	@Autowired
	CommentOrderDetailDAO cmtDAO;

	@ModelAttribute("productDetailList")
	public Map<ProductDetail, String> getproduct() {
		List<ProductDetail> products = productdetailDAO.findAll();
		Map<ProductDetail, String> map = new HashMap<>();
		for (ProductDetail c : products) {
			map.put(c, c.getProduct().getProductName() + "- Color:" + c.getColor() + "- Công Suất:" + c.getPower() + 'W'
					+ "- Size:" + c.getSize());
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
		/*
		 * List<ProductDetail> productdetails=productdetailDAO.findAll();
		 * model.addAttribute("producDetailtList",productdetails);
		 */
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
		

		long price = orderdetailDAO.findPriceByProductId(orderdetail.getProductDetail().getProduct().getProductId());
		orderdetail.setPrice(price);
		orderdetail.setId(0);
		orderdetailDAO.save(orderdetail);

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//		OrderDelieveryStatus deliverystt = new OrderDelieveryStatus();
//		orderdeliveryDAO.save(deliverystt);

		return "redirect:/admin/OrderDetail/" + OrderID;
	}

	@RequestMapping("/OrderDetail/{orderId}/edit/{orderDetailID}")
	public String Edit(@PathVariable("orderId") int orderID,

			@PathVariable("orderDetailID") int orderetailid, Model model) {
		model.addAttribute("title", "Quản lí hóa đơn chi tiết");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("OrderDetail", orderdetailDAO.getById(orderetailid));
		model.addAttribute("idOrder", orderID);
		Order od = odDAO.getById(orderID);
		model.addAttribute("OrderDetails", orderdetailDAO.findByOrder(od));
		System.out.println("oderDetailID" + orderdetailDAO.getById(orderetailid).getId());
		return "admin/OrderDetail-form";
	}

	@RequestMapping("/OrderDetail/{orderId}/update")
	public String update(@ModelAttribute("OrderDetail") OrderDetail orderdetail,@PathVariable("orderId") int orderID, Model model) {
		model.addAttribute("title", "Quản lí hóa đơn chi tiết");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		Order oder = odDAO.getById(orderID);
		orderdetail.setOrder(oder);
		model.addAttribute("idOrder", orderID);
		long price = orderdetailDAO.findPriceByProductId(orderdetail.getProductDetail().getProduct().getProductId());
		orderdetail.setPrice(price);
		Order od = odDAO.getById(orderID);
		orderdetailDAO.save(orderdetail);
		model.addAttribute("OrderDetails", orderdetailDAO.findByOrder(od));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//		OrderDelieveryStatus oddeliverystt = new OrderDelieveryStatus();
//		
//		deliveryDAO.save(deliverystt);
		return "redirect:/admin/OrderDetail/" + orderID;
	}

	@RequestMapping("/OrderDetail/{orderId}/delete")
	public String delete(@ModelAttribute("OrderDetail") OrderDetail orderdetail, @PathVariable("orderId") int orderID,
			BindingResult bd, Model model) {

		System.out.println("order Detail:" + orderdetail);
		Order oder = odDAO.getById(orderID);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<com.art.models.activity.Comment> cmt = cmtDAO.findByOrderDetail(orderdetail);
//		DeliveryStatus deliverystt = new DeliveryStatus();
//		deliverystt.setStatus(false);
//		deliverystt.setDate(new Date());
//		deliverystt.setOrderStatus(oder);
//		deliverystt.setDetailUpdate(
//
//				" Acccount  update :" + authentication.getName() + "Delete ID : " + orderdetail.getId() + " Product : "
//						+ orderdetail.getProduct().getProductId() + " Quantity :" + orderdetail.getQuantity());
//
//		deliveryDAO.save(deliverystt);
		for (com.art.models.activity.Comment comment : cmt) {
			cmtDAO.delete(comment);
		}
		orderdetailDAO.deleteById(orderdetail.getId());
		return "redirect:/admin/OrderDetail/" + orderID;
	}

//	@RequestMapping("/OrderDetail/{orderId}/history")
//	public String History(@PathVariable("orderId") int orderID, Model model) {
//		model.addAttribute("History", deliveryDAO.findByOrderStatus(odDAO.getById(orderID)));
//		return "/admin/DeliveryStatus-form";
//	}

}
