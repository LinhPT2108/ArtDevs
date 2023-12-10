package com.art.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.promotion.VoucherDAO;
import com.art.models.promotion.Voucher;
import com.art.utils.Path;

@RestController
@RequestMapping(Path.BASE_PATH)
public class VoucherController {

	@Autowired
	VoucherDAO voucherDAO;

	@GetMapping("/voucher")
	public ResponseEntity<List<Voucher>> getVoucher() {
		List<Voucher> lVoucher = voucherDAO.findAll();
		List<Voucher> voucherCurrents = new ArrayList<>();
		for (Voucher voucher : lVoucher) {
			if (voucher.getNumberOfUses() < voucher.getMaximumNumberOfUses() 
				&& voucher.getStartDay().after(new Date())
				&& voucher.getEndDay().before(new Date())) {
				voucherCurrents.add(voucher);
			}
		}
		return ResponseEntity.ok(voucherCurrents);
	}
}
