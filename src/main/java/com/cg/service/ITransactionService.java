package com.cg.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.bean.Customer;
import com.cg.bean.Transaction;
import com.cg.dao.ICustomerRepository;
import com.cg.dao.ITransactionRepository;
import com.cg.exception.CustomerNotFoundException;
import com.cg.exception.TransactionDetailsNotFoundException;

@Service
public class ITransactionService {

	List<String> cards = new ArrayList<String>(
			Arrays.asList("Visa", "MasterCard", "Discover", "American Express", "Diners Club", "JCB"));

	@Autowired
	private ITransactionRepository transactionRepo;

	@Autowired
	private ICustomerRepository custRepo;

	// Add Transaction for customer
	public ResponseEntity<Object> addTransaction(long id, Transaction transaction) {

		if (!custRepo.existsById(id)) {

			throw new CustomerNotFoundException(
					"Customer with id " + id + " doesn't exist. \nEnter a valid customer id.");
		}

		Customer customer = custRepo.findById(id).get();

		transaction.setCustomer(customer);

		transactionRepo.saveAndFlush(transaction);

		return new ResponseEntity<Object>("Transaction added", HttpStatus.ACCEPTED);
	}

	// Remove Transaction for the customer
	public ResponseEntity<Object> removeTransaction(long id, long transactionId) {

		if (!custRepo.existsById(id)) {

			throw new CustomerNotFoundException(
					"Customer with id " + id + " doesn't exist. \nEnter a valid customer id.");
		}

		if (!transactionRepo.existsById(transactionId)) {
			throw new TransactionDetailsNotFoundException(
					"Transaction Details Not Found.\nEnter the valid Transaction ID");
		}

		Transaction transaction = transactionRepo.findTransactionByIdAndTransactionId(id, transactionId);

		if (transaction == null) {

			throw new TransactionDetailsNotFoundException("Transaction Doesn't exist");
		}

		transactionRepo.deleteById(transactionId);

		return new ResponseEntity<Object>("Transaction deleted Successfully", HttpStatus.OK);
	}

	// Update the Transaction for a customer
	public ResponseEntity<Object> updateTransaction(long id, long transactionId, Transaction transaction) {

		if (!custRepo.existsById(id)) {

			throw new CustomerNotFoundException(
					"Customer with id " + id + " doesn't exist. \nEnter a valid customer id.");
		}

		if (!transactionRepo.existsById(transactionId)) {

			throw new TransactionDetailsNotFoundException(
					"Sorry, can't update as Transaction ID doesn't exits.\nEnter the valid Transaction ID");
		}

		Transaction transaction_original = transactionRepo.findTransactionByIdAndTransactionId(id, transactionId);

		if (transaction_original == null) {

			throw new TransactionDetailsNotFoundException("Transaction doesn't exist");
		}

		transaction_original.setCardNumber(transaction.getCardNumber());
		transaction_original.setTransactionDate(transaction.getTransactionDate());
		transaction_original.setStatus(transaction.getStatus());
		transaction_original.setPayment(transaction.getPayment());
		transaction_original.setDiscription(transaction.getDiscription());

		transactionRepo.saveAndFlush(transaction_original);

		return new ResponseEntity<Object>("Transaction Updated Successfully", HttpStatus.OK);
	}

	// Get Transaction for customer
	public Transaction getTransactionDetails(long id, long transactionId) {

		if (!custRepo.existsById(id)) {

			throw new CustomerNotFoundException(
					"Customer with id " + id + " doesn't exist. \nEnter a valid customer id.");
		}

		if (!transactionRepo.existsById(transactionId)) {
			throw new TransactionDetailsNotFoundException(
					"Transaction Details Not Found.\nEnter the valid Transaction ID");
		}

		Transaction transaction = transactionRepo.findTransactionByIdAndTransactionId(id, transactionId);

		if (transaction == null) {

			throw new TransactionDetailsNotFoundException("Transaction Doesn't exist");
		}

		return transaction;
	}

	// Get All Transaction of the customer
	public List<Transaction> getAllTransactions(long id) {
		if (!custRepo.existsById(id)) {

			throw new CustomerNotFoundException(
					"Customer with id " + id + " doesn't exist. \nEnter a valid customer id.");
		}

		return transactionRepo.findAllTransactionById(id);
	}

	
}
