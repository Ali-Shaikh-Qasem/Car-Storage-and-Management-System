package com.example.database_project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class employees {
    private SimpleIntegerProperty eid;
    private SimpleIntegerProperty salary;
    private SimpleStringProperty name;
    private SimpleStringProperty address;

    public employees(int eid, String name,String address,int salary) {
        this.eid = new SimpleIntegerProperty(eid);
        this.salary = new SimpleIntegerProperty(salary);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
    }

	public SimpleIntegerProperty getEid() {
		return eid;
	}

	public void setEid(SimpleIntegerProperty eid) {
		this.eid = eid;
	}

	public SimpleIntegerProperty getSalary() {
		return salary;
	}

	public void setSalary(SimpleIntegerProperty salary) {
		this.salary = salary;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getAddress() {
		return address;
	}

	public void setAddress(SimpleStringProperty address) {
		this.address = address;
	}

	public SimpleIntegerProperty eidProperty() {
		return eid;
	}

	public SimpleIntegerProperty salaryProperty() {
		return salary;
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public SimpleStringProperty addressProperty() {
		return address;
	}
}
