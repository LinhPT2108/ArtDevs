package com.art.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.art.dao.promotion.VoucherDAO;
import com.art.dao.user.AccountDAO;
import com.art.models.promotion.Voucher;
import com.art.models.user.Account;
import com.art.service.ParamService;

@Controller
@RequestMapping("/admin")
public class voucherController {
	@Autowired
	AccountDAO acDAO;

	@Autowired
	VoucherDAO vouDAO;

	@Autowired
	ParamService paramService;

	@RequestMapping("/voucher")
	public String getVoucher(@ModelAttribute("vouchers") Voucher voucher, Model model) {
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("ListVoucher", vouDAO.findAll());
		return "/admin/voucher-form";
	}

	@PostMapping("/voucher/create")
	public String createvoucher(@ModelAttribute("vouchers") Voucher voucher, BindingResult bd, Model model) {

		model.addAttribute("title", "Quản lí khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
//		List<Voucher> vouch=vouDAO.findAll();
//		model.addAttribute("flashSales",vouch);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account user2 = acDAO.findByEmail(authentication.getName());
		model.addAttribute("ListVoucher", vouDAO.findAll());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		String startDayStr = paramService.getString("startDay", "");
		System.out.println(startDayStr);
		String endDayStr = paramService.getString("endDay", "");
		System.out.println(endDayStr);
		int maxvoucher = voucher.getMaximumNumberOfUses();

		Double pricevoucher = voucher.getMaximumPriceDiscount();
		System.out.println("so luong voucher" + pricevoucher);
		boolean check = false;
		if (startDayStr == null || startDayStr.trim().isEmpty() || endDayStr == null || endDayStr.trim().isEmpty()
				|| maxvoucher == 0 || pricevoucher == 0) {
			if (startDayStr.isEmpty() || startDayStr.trim().isEmpty()) {
				model.addAttribute("start_dayError", "Vui lòng chọn thời gian bắt đầu");
			}
			if (endDayStr.isEmpty() || endDayStr.trim().isEmpty()) {
				model.addAttribute("end_dayError", "Vui lòng chọn thời gian kết thúc");
			}
			if (maxvoucher == 0) {
				model.addAttribute("maximumvoucher_Error", "Vui lòng nhập số lượng Voucher phát hành");
			}
			if (pricevoucher == 0) {
				model.addAttribute("maximumpricevoucher_Error", "Vui lòng nhập giá trị voucher");
			}
		} else {
			try {
				Date startDateTime = dateFormat.parse(startDayStr);
				Date endDateTime = dateFormat.parse(endDayStr);
				Date currentDateTime = new Date();
				if (startDateTime.after(endDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải trước ngày kết thúc");
					check = true;
				}

				if (startDateTime.before(currentDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải lớn hơn ngày hiện tại");
					check = true;
				}

				if (endDateTime.before(startDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
					check = true;
				}

				if (endDateTime.before(currentDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày hiện tại");
					check = true;
				}
			} catch (DateTimeParseException | ParseException e) {
				check = true;
				System.out.println(e);
				model.addAttribute("start_dayError", "Ngày không hợp lệ");
				model.addAttribute("end_dayError", "Ngày không hợp lệ");
			}
			if (check) {
				return "admin/voucher-form";
			} else {
				
				 try {
					 voucher.setStartDay(dateFormat.parse(startDayStr));
					voucher.setEndDay(dateFormat.parse(endDayStr));
					System.out.println("123+" + voucher);
					voucher.setUser(user2);
					vouDAO.save(voucher);

					return "redirect:/admin/voucher";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// List<Voucher> voucherrr=vouDAO.findAll();
				// Account userCus=sessionService.get("userLogin");
				// for (FlashSale f: createFS) {
				// if(!f.isStatus()) {
				// f.setStatus(true);
				// flashSaleDAO.save(f);
				// }
				// }
				// flashSale.setUser(userCus);
			

			}

		}

		return "admin/voucher-form";
	}
	@RequestMapping("/voucher/edit/{idvoucher}")
	public String EditVoucher(@PathVariable("idvoucher") int idvoucher,Model model ) {
		model.addAttribute("vouchers", vouDAO.getById(idvoucher));
		model.addAttribute("title", "Quản lí khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("ListVoucher", vouDAO.findAll());
		return "admin/voucher-form";
	}
}
