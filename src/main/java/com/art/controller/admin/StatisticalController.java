package com.art.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.art.dao.promotion.DeliveryStatusDAO;
import com.art.dao.promotion.OrderDAO;
import com.art.dao.promotion.OrderDeliveryStatusDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.dao.user.AccountDAO;
import com.art.models.product.Product;
import com.art.models.promotion.DeliveryStatus;
import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDelieveryStatus;

@Controller
@RequestMapping("/admin")
public class StatisticalController {

	@Autowired
	OrderDAO revenueService;

	@Autowired
	OrderDetailDAO idDAO;

	@Autowired
	AccountDAO uDAO;


	@Autowired
	OrderDeliveryStatusDAO dlvrDAO;
	
	@Autowired DeliveryStatusDAO statusDAO;


	@GetMapping("/statistical-revenue/daily-revenue")
	public ResponseEntity<?> getDailyRevenue() {
		// Gọi phương thức của dịch vụ để lấy dữ liệu doanh thu theo ngày
		List<String> labels = revenueService.getLabelsRevenueByDate();
		List<Double> values = revenueService.getPriceRevenueByDate();
		Map<String, Object> responseData = new HashMap<>();

		responseData.put("labels", labels);
		responseData.put("values", values);
		return ResponseEntity.ok(responseData);

	}

	@GetMapping("/statistical-revenue/monthly-revenue")
	public ResponseEntity<?> getMonthlyRevenue() {
		// Gọi phương thức của dịch vụ để lấy dữ liệu doanh thu theo tháng
		List<Integer> labels = revenueService.getMonthLabelsRevenue();
		List<Double> values = revenueService.getTotalAmountByMonth();

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("labels", labels);
		responseData.put("values", values);

		return ResponseEntity.ok(responseData);
	}

	@GetMapping("/statistical-revenue/yearly-revenue")
	public ResponseEntity<?> getYearlyRevenue() {
		// Gọi phương thức của dịch vụ để lấy dữ liệu doanh thu theo năm
		List<Integer> labels = revenueService.getYearLabelsRevenue();
		List<Double> values = revenueService.getTotalAmountByYear();

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("labels", labels);
		responseData.put("values", values);

		return ResponseEntity.ok(responseData);
	}

	// Chỉnh lại phần invoice -> order dòng 83
	@GetMapping("/statistical-order")
	public String showOrders(@ModelAttribute("pd") Product pd, Model model) {

//		model.addAttribute("views", "order-form");
		model.addAttribute("title", "Thống kê đơn hàng");
		List<Order> od = revenueService.findAllByOrderByOrderDateDesc();

		for (Order order : od) {
			List<OrderDelieveryStatus> myList = new ArrayList<>();
			myList.add(dlvrDAO.sortByDate(order.getId()).get(0));
			order.setOrderStatus(myList);
		}
		model.addAttribute("statusList",statusDAO.findAll() );
		model.addAttribute("invoice", od);
		return "admin/statisticaloder-form";
	}

	@PostMapping("/update-status")

	public ResponseEntity<Boolean> updateStatus(@RequestParam int itemId, @RequestParam String status) {


		// Gọi phương thức service để cập nhật trạng thái hóa đơn
		try {
			updateStatusOrder(itemId, status);
			System.out.println("123qwe");
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}

	public void updateStatusOrder(int itemId, String status) {
		Optional<Order> optionalInvoice = Optional.of(revenueService.findById(itemId));
		if (optionalInvoice.isPresent()) {
			Order invoice = optionalInvoice.get();
			for (OrderDelieveryStatus p : invoice.getOrderStatus()) {
				p.setStatus(false);
				dlvrDAO.save(p);
			}
			
			List<DeliveryStatus> deli = statusDAO.findByNameStatus(status);
			System.out.println("deli" + deli);
//            invoice.setStatus(status);
			
			OrderDelieveryStatus odrsst = new OrderDelieveryStatus();
			odrsst.setDate(new Date());
			odrsst.setOrderStatus(invoice);
			odrsst.setStatus(true);
			odrsst.setDeliveryStatus(deli.get(0));
			dlvrDAO.save(odrsst);


		}
	}

	@GetMapping("/statistical-wishlist")
	public String showWishlists(@ModelAttribute("pd") Product pd, Model model) {

		model.addAttribute("views", "wishlist-form");
		model.addAttribute("title", "Thống kê sản phẩm theo lượt yêu thích");

		return "admin/index";
	}

	@GetMapping("/statistical-best-seller")
	public String showBestSeller(@ModelAttribute("pd") Product pd, Model model) {

		model.addAttribute("views", "best-seller-form");
		model.addAttribute("title", "Thống kê sản phẩm bán chạy");


		
		model.addAttribute("bestSellers", idDAO.countProductsOrderByCountDesc());


		// sản phẩm bán chạy

		return "admin/static-best-seller";
	}

	// Chỉnh lại phần product -> product detail dòng 132
	@GetMapping("/statistical-orders-by-user")
	public String showOrdersByUser(@ModelAttribute("pd") Product pd, Model model) {

		model.addAttribute("views", "orders-by-user-form");
		model.addAttribute("title", "Thống kê đơn hàng theo tài khoản");
		model.addAttribute("ordersByUser", uDAO.getUsersWithOrdersCount());

		return "admin/orders-by-user-form";
	}
}
