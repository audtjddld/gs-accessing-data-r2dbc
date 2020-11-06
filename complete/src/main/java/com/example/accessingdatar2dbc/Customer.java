package com.example.accessingdatar2dbc;

import java.util.List;
import org.springframework.data.annotation.Id;

public class Customer {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private List<Account> accounts;

    public Customer(String firstName, String lastName, List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = accounts;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
    public String toString() {
        return String.format(
            "Customer[id=%d, firstName='%s', lastName='%s']",
            id, firstName, lastName);
    }

  public List<Account> getAccounts() {
    return accounts;
  }

}
