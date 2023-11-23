package com.art.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.art.dao.promotion.FlashSaleDAO;
import com.art.models.promotion.FlashSale;
import com.art.service.ParamService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class flashSaleController {
	@Autowired
	FlashSaleDAO flashSaleDAO;
	@Autowired
	HttpServletResponse response;
	@Autowired
	ParamService paramService;
	
	@GetMapping("/flashSale")
	public String FlashSale(@ModelAttribute("flashSale") FlashSale flashSale,Model model) {	
		model.addAttribute("views", "flashSale-form");
		model.addAttribute("title", "Quản lí khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("start_dayError", "");
		List<FlashSale> flashSales=flashSaleDAO.findAll();
		model.addAttribute("flashSales",flashSales);
		return "admin/flashsale-form";
	}

	@PostMapping("/flashSale/create")
	public String createflashSale(@ModelAttribute("flashSale") FlashSale flashSale, BindingResult bd,Model model) {
		model.addAttribute("views", "flashSale-form");
		model.addAttribute("title", "Quản lí khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
		List<FlashSale> flashSales=flashSaleDAO.findAll();
		model.addAttribute("flashSales",flashSales);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		String startDayStr = paramService.getString("startDay","");
		System.out.println(startDayStr);
		String endDayStr = paramService.getString("endDay","");
		System.out.println(endDayStr);
		boolean check=false;
		if (startDayStr == null || startDayStr.trim().isEmpty() || endDayStr == null
				|| endDayStr.trim().isEmpty()) {
			if (startDayStr.isEmpty() || startDayStr.trim().isEmpty()) {
				model.addAttribute("start_dayError", "Vui lòng chọn thời gian bắt đầu");
			}
			if (endDayStr.isEmpty() || endDayStr.trim().isEmpty()) {
				model.addAttribute("end_dayError", "Vui lòng chọn thời gian kết thúc");
			}
		} else {
			try {
				Date startDateTime = dateFormat.parse(startDayStr);
				Date endDateTime = dateFormat.parse(endDayStr);
				Date currentDateTime = new Date();
				if (startDateTime.after(endDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải trước ngày kết thúc");
					check=true;
				}

				if (startDateTime.before(currentDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải lớn hơn ngày hiện tại");
					check=true;
				}

				if (endDateTime.before(startDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
					check=true;
				}

				if (endDateTime.before(currentDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày hiện tại");
					check=true;
				}
			}catch(DateTimeParseException | ParseException e) {
				check=true;
				System.out.println(e);
				model.addAttribute("start_dayError", "Ngày không hợp lệ");
				model.addAttribute("end_dayError", "Ngày không hợp lệ");
			}
			if(check) {
				return "admin/index";
			}else {				
				try {
					flashSale.setStartDay(dateFormat.parse(startDayStr));
					flashSale.setEndDay(dateFormat.parse(endDayStr));
					List<FlashSale> createFS=flashSaleDAO.findAll();
//					Account userCus=sessionService.get("userLogin");
//					for (FlashSale f: createFS) {
//						if(!f.isStatus()) {
//						f.setStatus(true);
//						flashSaleDAO.save(f);
//						}
//					}
//					flashSale.setUser(userCus);
					flashSaleDAO.save(flashSale);
					return "redirect:/admin/flashSale";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
				
			}
			
		}
		
		return "admin/index";
	}
	@RequestMapping("/flashSale/edit/{id}")
	public String editflashSale(@ModelAttribute("flashSale") FlashSale flashSale,Model model,@PathVariable("id") Integer id) {
		try {
			model.addAttribute("views", "flashSale-form");
			model.addAttribute("title", "Quản lí khuyến mãi");
			model.addAttribute("typeButton", "Thêm");
			model.addAttribute("updateButton", "Cập nhật"); 
			model.addAttribute("deleteButton", "Xóa");
			List<FlashSale> flashSales=flashSaleDAO.findAll();
			FlashSale flashsalebyid=flashSaleDAO.findById(id).get();
			model.addAttribute("flashSale",flashsalebyid);
			model.addAttribute("flashSales",flashSales);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/index";
	}
	@RequestMapping("/flashSale/update")
	public String updateflashSale(@ModelAttribute("flashSale") FlashSale flashSale,BindingResult bd,Model model) {
		model.addAttribute("views", "flashSale-form");
		model.addAttribute("title", "Quản lí khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
//		if(bd.hasErrors()) {
//			return "admin/index";
//		}
		List<FlashSale> flashSales=flashSaleDAO.findAll();
		model.addAttribute("flashSales",flashSales);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		String startDayStr = paramService.getString("startDay","");
		System.out.println(startDayStr);
		String endDayStr = paramService.getString("endDay","");
		System.out.println(endDayStr);
		boolean check=false;
		if (startDayStr == null || startDayStr.trim().isEmpty() || endDayStr == null
				|| endDayStr.trim().isEmpty()) {
			if (startDayStr.isEmpty() || startDayStr.trim().isEmpty()) {
				model.addAttribute("start_dayError", "Vui lòng chọn thời gian bắt đầu");
			}
			if (endDayStr.isEmpty() || endDayStr.trim().isEmpty()) {
				model.addAttribute("end_dayError", "Vui lòng chọn thời gian kết thúc");
			}
		} else {
			try {
				Date startDateTime = dateFormat.parse(startDayStr);
				Date endDateTime = dateFormat.parse(endDayStr);
				Date currentDateTime = new Date();
				if (startDateTime.after(endDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải trước ngày kết thúc");
					check=true;
				}

				if (startDateTime.before(currentDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải lớn hơn ngày hiện tại");
					check=true;
				}

				if (endDateTime.before(startDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
					check=true;
				}

				if (endDateTime.before(currentDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày hiện tại");
					check=true;
				}
			}catch(DateTimeParseException | ParseException e) {
				check=true;
				System.out.println(e);
				model.addAttribute("start_dayError", "Ngày không hợp lệ");
				model.addAttribute("end_dayError", "Ngày không hợp lệ");
			}
			if(check) {
				return "admin/index";
			}else {				
				try {
					flashSale.setStartDay(dateFormat.parse(startDayStr));
					flashSale.setEndDay(dateFormat.parse(endDayStr));
					List<FlashSale> createFS=flashSaleDAO.findAll();
//					Account userCus=sessionService.get("userLogin");
//					for (FlashSale f: createFS) {
//						if(!f.isStatus()) {
//						f.setStatus(true);
//						flashSaleDAO.save(f);
//						}
//					}
//					flashSale.setUser(userCus);
					flashSaleDAO.save(flashSale);
					return "redirect:/admin/flashSale";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
				
			}
			
		}
		return "redirect:/admin/flashSale/edit/"+flashSale.getId();
	}
	@RequestMapping("/flashSale/delete/{id}")
	public String deleteflashSale(@PathVariable("id") Integer id,@ModelAttribute("flashSale") FlashSale flashSale,BindingResult bd,Model model) {
		model.addAttribute("views", "flashSale-form");
		model.addAttribute("title", "Quản lí khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		String startDayStr = paramService.getString("startDay","");
		System.out.println(startDayStr);
		String endDayStr = paramService.getString("endDay","");
		System.out.println(endDayStr);
		boolean check=false;
		if (startDayStr == null || startDayStr.trim().isEmpty() || endDayStr == null
				|| endDayStr.trim().isEmpty()) {
			if (startDayStr.isEmpty() || startDayStr.trim().isEmpty()) {
				model.addAttribute("start_dayError", "Vui lòng chọn thời gian bắt đầu");
			}
			if (endDayStr.isEmpty() || endDayStr.trim().isEmpty()) {
				model.addAttribute("end_dayError", "Vui lòng chọn thời gian kết thúc");
			}
		} else {
			try {
				Date startDateTime = dateFormat.parse(startDayStr);
				Date endDateTime = dateFormat.parse(endDayStr);
				Date currentDateTime = new Date();
				if (startDateTime.after(endDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải trước ngày kết thúc");
					check=true;
				}

				if (startDateTime.before(currentDateTime)) {
					model.addAttribute("start_dayError", "Ngày bắt đầu phải lớn hơn ngày hiện tại");
					check=true;
				}

				if (endDateTime.before(startDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
					check=true;
				}

				if (endDateTime.before(currentDateTime)) {
					model.addAttribute("end_dayError", "Ngày kết thúc phải lớn hơn ngày hiện tại");
					check=true;
				}
			}catch(DateTimeParseException | ParseException e) {
				check=true;
				System.out.println(e);
				model.addAttribute("start_dayError", "Ngày không hợp lệ");
				model.addAttribute("end_dayError", "Ngày không hợp lệ");
			}
			if(check) {
				return "admin/index";
			}else {				
				try {
//					Account userCus=sessionService.get("userLogin");
					flashSale.setStartDay(dateFormat.parse(startDayStr));
					flashSale.setEndDay(dateFormat.parse(endDayStr));
//					flashSale.setUser(userCus);
					flashSale.setStatus(true);
					flashSaleDAO.save(flashSale);
					return "redirect:/admin/flashSale";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
				
			}
			
		}
		
//		flashSaleDAO.deleteById(id);
		return "redirect:/admin/flashSale";
	}
}
