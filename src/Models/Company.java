package Models;

import java.util.List;

public class Company {
    public int companyId;
    public String cvr;
    public double trustScore;
    public int numberOfVotes;
    public String name;
    public String address;
    public String postCode;
    public String logo;
    public List<Employee> employees;
    public String connectionAddress;

    public Company(int companyId, String cvr, double trustScore, int numberOfVotes,
                   String name, String address, String postCode, String logo,
                   List<Employee> employees, String connectionAddress) {
        this.companyId = companyId;
        this.cvr = cvr;
        this.trustScore = trustScore;
        this.numberOfVotes = numberOfVotes;
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.logo = logo;
        this.employees = employees;
        this.connectionAddress = connectionAddress;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }

    public double getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(double trustScore) {
        this.trustScore = trustScore;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getConnectionAddress() {
        return connectionAddress;
    }

    public void setConnectionAddress(String connectionAddress) {
        this.connectionAddress = connectionAddress;
    }
}
