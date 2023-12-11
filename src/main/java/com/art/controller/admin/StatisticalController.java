package com.art.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.DeliveryStatusDAO;
import com.art.dao.promotion.OrderDAO;
import com.art.dao.promotion.OrderDeliveryStatusDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.dao.user.AccountDAO;
import com.art.models.product.Product;
import com.art.models.promotion.DeliveryStatus;
import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDelieveryStatus;
import com.art.utils.Path;

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
	ProductDAO productDAO;

	@Autowired
	OrderDAO orderDAO;

	@Autowired
	OrderDeliveryStatusDAO dlvrDAO;

	@Autowired
	DeliveryStatusDAO statusDAO;

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
		model.addAttribute("statusList", statusDAO.findAll());
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
	@GetMapping("/statistical-product-with-quantity")
	public String showOrdersByUser(@ModelAttribute("pd") Product pd, Model model) {

		model.addAttribute("views", "product-with-quantity");
		model.addAttribute("title", "Sản phẩm sắp hết hàng");
		model.addAttribute("productWithQuantity", productDAO.getProductsWithTotalQuantity());
		System.out.println("123123 :" + productDAO.getProductsWithTotalQuantity());
		return "admin/product-with-quantity";
	}

	@GetMapping("/statistical-order-count-by-status")
	public String showOrderCountByStatus(Model model) {

		model.addAttribute("views", "statistical-order");
		model.addAttribute("title", "Thống kê đơn hàng");

		model.addAttribute("dayProccessing", Path.getCountOrderByStatus(0, 1, orderDAO));
		model.addAttribute("dayShipping", Path.getCountOrderByStatus(0, 2, orderDAO));
		model.addAttribute("daySuccess", Path.getCountOrderByStatus(0, 3, orderDAO));
		model.addAttribute("dayCancel", Path.getCountOrderByStatus(0, 4, orderDAO));

		model.addAttribute("weekProccessing", Path.getCountOrderByStatus(7, 1, orderDAO));
		model.addAttribute("weekShipping", Path.getCountOrderByStatus(7, 2, orderDAO));
		model.addAttribute("weekSuccess", Path.getCountOrderByStatus(7, 3, orderDAO));
		model.addAttribute("weekCancel", Path.getCountOrderByStatus(7, 4, orderDAO));

		model.addAttribute("monthProccessing", Path.getCountOrderByStatus(30, 1, orderDAO));
		model.addAttribute("monthShipping", Path.getCountOrderByStatus(30, 2, orderDAO));
		model.addAttribute("monthSuccess", Path.getCountOrderByStatus(30, 3, orderDAO));
		model.addAttribute("monthCancel", Path.getCountOrderByStatus(30, 4, orderDAO));

		model.addAttribute("yearProccessing", Path.getCountOrderByStatus(365, 1, orderDAO));
		model.addAttribute("yearShipping", Path.getCountOrderByStatus(365, 2, orderDAO));
		model.addAttribute("yearSuccess", Path.getCountOrderByStatus(365, 3, orderDAO));
		model.addAttribute("yearCancel", Path.getCountOrderByStatus(365, 4, orderDAO));

		return "admin/statistical-order";
	}

	@GetMapping("/order-count-by-status")
	public ResponseEntity<Map<String, Long>> getOrder(Model model) {

		Map<String, Long> getOrders = new LinkedHashMap<String, Long>();
		getOrders.put("proccessing", Path.getCountOrderByStatus(7, 1, orderDAO));
		getOrders.put("shipping", Path.getCountOrderByStatus(7, 2, orderDAO));
		getOrders.put("success", Path.getCountOrderByStatus(7, 3, orderDAO));
		getOrders.put("cancel", Path.getCountOrderByStatus(7, 4, orderDAO));

		return ResponseEntity.ok(getOrders);
	}
	@GetMapping("/order-count-by-status-byday")
	public ResponseEntity<Map<String, Long>> getOrderbydate (Model model) {

		Map<String, Long> getOrders = new LinkedHashMap<String, Long>();
		
		getOrders.put("proccessing", Path.getCountOrderByStatus(0, 1, orderDAO));
		getOrders.put("shipping", Path.getCountOrderByStatus(0, 2, orderDAO));
		getOrders.put("success", Path.getCountOrderByStatus(0, 3, orderDAO));
		getOrders.put("cancel", Path.getCountOrderByStatus(0, 4, orderDAO));

		return ResponseEntity.ok(getOrders);
	}
	
	@GetMapping("/order-count-by-status-total")
	public ResponseEntity<Map<String, Map<String, Long>>> getOrders(Model model) {
		Map<String, Map<String, Long>> getOrders = new LinkedHashMap<>();
		Map<String, Long> getOrdersByDay = new LinkedHashMap<String, Long>();
		getOrdersByDay.put("proccessing", Path.getCountOrderByStatus(0, 1, orderDAO));
		getOrdersByDay.put("shipping", Path.getCountOrderByStatus(0, 2, orderDAO));
		getOrdersByDay.put("success", Path.getCountOrderByStatus(0, 3, orderDAO));
		getOrdersByDay.put("cancel", Path.getCountOrderByStatus(0, 4, orderDAO));

		Map<String, Long> getOrdersByWeek = new LinkedHashMap<String, Long>();
		getOrdersByWeek.put("proccessing", Path.getCountOrderByStatus(7, 1, orderDAO));
		getOrdersByWeek.put("shipping", Path.getCountOrderByStatus(7, 2, orderDAO));
		getOrdersByWeek.put("success", Path.getCountOrderByStatus(7, 3, orderDAO));
		getOrdersByWeek.put("cancel", Path.getCountOrderByStatus(7, 4, orderDAO));
		
		Map<String, Long> getOrdersByMoth = new LinkedHashMap<String, Long>();
		getOrdersByMoth.put("proccessing", Path.getCountOrderByStatus(30, 1, orderDAO));
		getOrdersByMoth.put("shipping", Path.getCountOrderByStatus(30, 2, orderDAO));
		getOrdersByMoth.put("success", Path.getCountOrderByStatus(30, 3, orderDAO));
		getOrdersByMoth.put("cancel", Path.getCountOrderByStatus(30, 4, orderDAO));
		
		Map<String, Long> getOrdersByYear = new LinkedHashMap<String, Long>();
		getOrdersByYear.put("proccessing", Path.getCountOrderByStatusAndYear( 1, orderDAO));
		getOrdersByYear.put("shipping", Path.getCountOrderByStatusAndYear( 2, orderDAO));
		getOrdersByYear.put("success", Path.getCountOrderByStatusAndYear( 3, orderDAO));
		getOrdersByYear.put("cancel", Path.getCountOrderByStatusAndYear( 4, orderDAO));
		
		getOrders.put("day", getOrdersByDay);
		getOrders.put("week", getOrdersByWeek);
		getOrders.put("month", getOrdersByMoth);
		getOrders.put("year", getOrdersByYear);
		
		return ResponseEntity.ok(getOrders);
	}
}
