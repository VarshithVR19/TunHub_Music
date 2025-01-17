package com.example.demo.controllers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entites.Users;
import com.example.demo.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class PaymentController {
	@Autowired
	UsersService service;

	@GetMapping("/pay")
	public String pay() {
		return "pay";
	}
	
	@GetMapping("/payment-success")
	public String paymentSuccess(HttpSession session) {
		String mail = (String) session.getAttribute("email");
		Users u = service.getUser(mail);
		u.setPremium(true);
		service.updateUser(u);
		return "customerHome";
	}
	
	@GetMapping("/payment-failure")
	public String paymentFailure() {
		return "customerHome";
	}
	
	@SuppressWarnings("finally")
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder(HttpSession session) {

		int  amount  = 5000;
		Order order=null;
		try {
			RazorpayClient razorpay=new RazorpayClient("rzp_test_ltoZXJb9juQbkZ", "bhDa7nTo9OOUU3AsFWLRQDyx");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount*100); // amount in the smallest currency unit
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");

			order = razorpay.orders.create(orderRequest);

			String mail =  (String) session.getAttribute("email");

			Users u = service.getUser(mail);
			u.setPremium(true);
			service.updateUser(u);

		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		finally {
			return order.toString();
		}
	}
	
	@PostMapping("/verify")
	@ResponseBody
	public boolean postMethodName(@RequestBody String orderId ,@RequestBody String paymentId, @RequestParam String signature) {
		try {
			RazorpayClient razorpayClient=new RazorpayClient("rzp_test_j2XbgMaZhb9ULG", "1Hwf0i0sBnfeHXSqOlOqY5MH");
			
			String verificationDate = orderId + " " + paymentId;
			
			boolean isValidSignature = Utils.verifySignature(verificationDate, signature, "1Hwf0i0sBnfeHXSqOlOqY5MH");
			
			return isValidSignature;
		} catch (RazorpayException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	
}
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class PaymentController {
//  @GetMapping("/pay")
//  public String pay(){
//	return "pay";
//   }
//}