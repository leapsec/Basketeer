package com.example.Order_Service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Order_Service.dto.OrderItemRequest;
import com.example.Order_Service.model.OrderItem;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaypalService {
	private final APIContext apicontext;
	public Payment createPayment(Double total,String desc,List<OrderItemRequest> ords) throws PayPalRESTException {
		Payment p1 = new Payment();
		p1.setIntent("sale");
		Transaction nt = new Transaction();
		nt.setAmount(new Amount("USD", total.toString()));
		List<Item> il = new ArrayList<Item>();
		for(OrderItemRequest oi:ords) {
			Item ni = new Item();
			ni.setSku(Long.toString(oi.getProductid()));
			ni.setPrice(Float.toString(oi.getPrice()));
			ni.setCurrency("USD");
			ni.setQuantity(Integer.toString(oi.getQuantity()));
			il.add(ni);
		}
		ItemList il2 = new ItemList();
		il2.setItems(il);
		nt.setItemList(il2);
		nt.setDescription(desc);
		List<Transaction> lt = new ArrayList<Transaction>();
		lt.add(nt);
		p1.setTransactions(lt);
		p1.setPayer(new Payer().setPaymentMethod("paypal"));
		RedirectUrls redirectUrls = new RedirectUrls();
	    redirectUrls.setCancelUrl("http://localhost:5173/shop/paypal-cancel"); 
	    redirectUrls.setReturnUrl("http://localhost:5173/shop/paypal-return");
	    p1.setRedirectUrls(redirectUrls);
	    return p1.create(apicontext);
	}
	
	public Payment executePayment(String paymentId,String payerId) throws PayPalRESTException {
		Payment pay= new Payment();
		pay.setId(paymentId);
		
		PaymentExecution payexec = new PaymentExecution();
		payexec.setPayerId(payerId);
		return pay.execute(apicontext,payexec);
	}
}
