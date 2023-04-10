package com.dollarsbank.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.utility.ColorsUtility;
import com.dollarsbank.utility.DataGenerator;
public class DollarsBankController {
	private List<Customer> userList;
	private List<Account> accountList;
	private int currentUserIndex = 0;	
	
	public DollarsBankController() {
		//populating lists with the test data
		userList = new ArrayList<Customer>();
		for (int i = 0; i < DataGenerator.address.length; i++) {
			Customer customer = new Customer();
			customer.setName(DataGenerator.username[i]);
			customer.setAddress(DataGenerator.address[i]);
			customer.setContact_number(DataGenerator.phone[i]);
			customer.setUser_id(DataGenerator.user_id[i]);
			userList.add(customer);
		}
		
		accountList = new ArrayList<Account>();
		for (int i = 0; i < DataGenerator.user_id.length; i++) {
			accountList.add(new Account(DataGenerator.user_id[i],DataGenerator.password[i],DataGenerator.bal[i]));
		}
	}
	

	//lists customer information of all users of the bank
	public void getUserListInfo() {
		for(Customer c:userList) {
			System.out.println(c);
			System.out.println();
		}
		
	}
	
	//lists account info of all users
	public void getAccListInfo() {
		for(Account a:accountList) {
			System.out.println(a);
			System.out.println();
		}
		
	}
	
	//checks if user id entered by user is available
	public boolean checkUserId(String id) {
		for(int i =0;i< userList.size();i++) {
			if(userList.get(i).getUser_id().equals(id)) {
				return false; //unavailable
			}
		}
		return true;//available
	}
	
	//checks if contact number is a valid number
	public boolean validnum(String n) {
		if( n.length()!=10) {
			return false;
		}
		
		try {
			 double num = Double.parseDouble(n);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	//checks if password entered by user meets certain criteria
	public boolean checkPassValidity(String pw) {
		boolean hasUpper = false;
		boolean hasLower = false;
		boolean hasSpecial = false;
		if(pw.length()<8) {
			return false;
		}
		
		for(int i =0;i<pw.length();i++) {
			if(Character.isUpperCase(pw.charAt(i))){
				hasUpper=true;
			}
			if(Character.isLowerCase(pw.charAt(i))){
				hasLower=true;
			}
		}
		
		Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
	    Matcher m = special.matcher(pw);
	    if(m.find()) {
	    	hasSpecial = true;
	    }
	    
	    if(hasUpper && hasLower && hasSpecial) {
	    	return true;
	    }
	    return false;
	}
		
	//add's new user
	public void addCustomer(Customer c){
		userList.add(c);
	}
	
	//login check
	public boolean checkCredentials(String entered_id,String entered_pw) {
		for(int i =0;i< userList.size();i++) {
			if(userList.get(i).getUser_id().equals(entered_id)) {
				for(int j =0;j< accountList.size();j++) {
					if(accountList.get(j).getUser_id().equals(entered_id)) {
						if(accountList.get(j).getPassword().equals(entered_pw)) {
							currentUserIndex = i;
							return true;
						}
				}
			}
		}
	}
		return false;
	}
	
	//creates new account for a newly registered user
	public void addAcc(Account a){
		accountList.add(a);
	}
	
	//deposit money into user's account
	public boolean deposit(double amt) {
		accountList.get(currentUserIndex).setBalance(accountList.get(currentUserIndex).getBalance()+amt);
		accountList.get(currentUserIndex).setTransactions("Deposit Amount "+ amt+ " in account "+accountList.get(currentUserIndex).getUser_id()+". Balance - "+ accountList.get(currentUserIndex).getBalance());
		return true;
	}
	
	//returns current balance of the user
	public String currBalance() {
		return String.valueOf(accountList.get(currentUserIndex).getBalance());
	}
	
	//withdraws money from the user's account
	public boolean withdraw(double amt) {
		if(amt>accountList.get(currentUserIndex).getBalance()) {
			return false;
		}
		else {
			accountList.get(currentUserIndex).setBalance(accountList.get(currentUserIndex).getBalance()-amt);
			accountList.get(currentUserIndex).setTransactions("Withdraw Amount "+ amt+ " from account "+accountList.get(currentUserIndex).getUser_id()+". Balance - "+ accountList.get(currentUserIndex).getBalance());
			return true;
		}
		
	}
	
	//check if amount entered for money transfer is valid
	public boolean transferCheck(double amt) {
		if(amt>accountList.get(currentUserIndex).getBalance()) {
			return false;
		}
		return true;
	}
	
	//check if uid entered for money transfer is valid
	public boolean transferCheckId(String id) {
		boolean validPerson = false;
		
		for(int i =0;i< userList.size();i++) {
			if(userList.get(i).getUser_id().equals(id) && i!=currentUserIndex) {
				validPerson =true;
				break;
			}
		}
		
		return validPerson;
	}
	
	//transfer money from user acc to other acc
	public boolean transfer(String id, double amt) {
		//update user balance
		accountList.get(currentUserIndex).setBalance(accountList.get(currentUserIndex).getBalance()-amt);
		//add to transaction list
		accountList.get(currentUserIndex).setTransactions("Money Transfer Amount "+ amt+ " from account "+accountList.get(currentUserIndex).getUser_id()+" to "+id+". Balance - "+ accountList.get(currentUserIndex).getBalance());
		
		int recieverIndex =-1;
		for(int i =0;i< userList.size();i++) {
			if(userList.get(i).getUser_id().equals(id)) {
				recieverIndex=i;
				break;
			}
		}
		
		//update reciever balance
		accountList.get(recieverIndex).setBalance(accountList.get(recieverIndex).getBalance()+amt);
		//add to reciever transaction list
		accountList.get(recieverIndex).setTransactions("Money Transfer Amount "+ amt+ " recieved from account "+accountList.get(currentUserIndex).getUser_id()+". Balance - "+ accountList.get(recieverIndex).getBalance());
		return true;
	}
	
	//prints out customer information
	public void customerInfo() {
		System.out.println(ColorsUtility.ANSI_BLUE+ "+-----------------------+");
		System.out.println("| Customer Information: |");
		System.out.println("+-----------------------+" );
		System.out.println(userList.get(currentUserIndex));
		System.out.println(accountList.get(currentUserIndex));
	}
	
	//prints out user's 5 most recent transactions; recent on top & old on bottom
	public void recentTransactions() {
		System.out.println(ColorsUtility.ANSI_BLUE+ "+------------------------+");
		System.out.println("| 5 Recent Transactions: |");
		System.out.println("+------------------------+");
		//System.out.println("Most Recent Transactions[Most Recent to Oldest]:");
		ArrayList<String> currTransactions = accountList.get(currentUserIndex).getTransactions();
		if(currTransactions.size()>5) {
			int ctr = 1;
			for(int i=currTransactions.size()-1;i>=currTransactions.size()-5;i--) {
				System.out.println(ctr +". " +currTransactions.get(i));
				ctr++;
			}
		}
		else {
			int ctr2 = 1;
			for(int j= currTransactions.size()-1;j>=0;j--) {
				System.out.println(ctr2 +". " +currTransactions.get(j));
				ctr2++;
			}
		}
		
	}
}
