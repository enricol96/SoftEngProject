package it.polito.latazza.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class DataImpl implements DataInterface {
	
	private List<Transaction> transactions = new ArrayList<>();
	private Map<Integer, Employee> employees = new HashMap<>();
	private Map<Integer, Beverage> beverages = new HashMap<>();
	private LaTazzaAccount laTazzaAccount = new LaTazzaAccount();

	@Override
	public Integer sellCapsules(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount)
			throws EmployeeException, BeverageException, NotEnoughCapsules {
		Employee e = employees.get(employeeId);
		Beverage b = beverages.get(beverageId);
		if(e==null)
			throw new EmployeeException();
		if(b==null)
			throw new BeverageException();
		b.decreaseAvailableQuantity(numberOfCapsules);
		if(fromAccount)
			e.decreaseBalance(b.getCapsulesPrice()*numberOfCapsules);
		else
			laTazzaAccount.increaseBalance(b.getCapsulesPrice()*numberOfCapsules);
		Date d = Date.from(Instant.now());
		transactions.add(new Consumption());
		//TODO: what to return???
		return 0;
	}

	@Override
	public void sellCapsulesToVisitor(Integer beverageId, Integer numberOfCapsules)
			throws BeverageException, NotEnoughCapsules {
		Beverage b = beverages.get(beverageId);
		if(b==null)
			throw new BeverageException();
		b.decreaseAvailableQuantity(numberOfCapsules);
		laTazzaAccount.increaseBalance(b.getCapsulesPrice()*numberOfCapsules);
		Date d = Date.from(Instant.now());
		transactions.add(new Consumption());
	}

	@Override
	public Integer rechargeAccount(Integer id, Integer amountInCents) throws EmployeeException {
		Employee e = employees.get(id);
		if(e==null)
			throw new EmployeeException();
		e.decreaseBalance(amountInCents);
		laTazzaAccount.increaseBalance(amountInCents);
		Date d = Date.from(Instant.now());
		transactions.add(new Recharge());
		//TODO: what to return 
		return 0;
	}

	@Override
	public void buyBoxes(Integer beverageId, Integer boxQuantity) throws BeverageException, NotEnoughBalance {
		Beverage b = beverages.get(beverageId);
		if(b==null)
			throw new BeverageException();
		b.increaseAvailableQuantity(boxQuantity);
		laTazzaAccount.decreaseBalance(b.getBoxPrice()*boxQuantity);
		Date d = Date.from(Instant.now());
		transactions.add(new BoxPurchase());
	}

	@Override
	public List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate)
			throws EmployeeException, DateException {
		return transactions.stream().filter(l -> (l instanceof Consumption) && ((Consumption)l).getEmployee().getId() == employeeId && l.getDate().after(startDate) && l.getDate().before(endDate)).map(l -> l.toString()).collect(java.util.stream.Collectors.toList());
	}

	@Override
	public List<String> getReport(Date startDate, Date endDate) throws DateException {
		return transactions.stream().filter(l -> l.getDate().after(startDate) && l.getDate().before(endDate)).map(l -> l.toString()).collect(java.util.stream.Collectors.toList());
	}

	@Override
	public Integer createBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getBeveragesId() {
		// TODO Auto-generated method stub
		return new ArrayList<Integer>();
	}

	@Override
	public Map<Integer, String> getBeverages() {
		// TODO Auto-generated method stub
		return new HashMap<Integer, String>();
	}

	@Override
	public Integer getBeverageCapsules(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getEmployeesId() {
		// TODO Auto-generated method stub
		return new ArrayList<Integer>();
	}

	@Override
	public Map<Integer, String> getEmployees() {
		// TODO Auto-generated method stub
		return new HashMap<Integer, String>();
	}

	@Override
	public Integer getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
