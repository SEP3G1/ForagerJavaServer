package Controllers;

import Models.Company;
import Models.Listing;
import Models.Report;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CompanyController implements ICompanyController
{

    private ICommunicationController communicationController;
    private BufferedReader rd;

    public CompanyController(ICommunicationController communicationController)
    {
        this.communicationController = communicationController;
    }

    @Override public String getCompany(String id) throws IOException
    {
        rd = communicationController.HttpGetRequest("/api/company/" + id);

        String line = "";
        ObjectMapper objectMapper = new ObjectMapper();
        Company company = null;
        //Read body
        while ((line = rd.readLine()) != null) {
            if (line != null){
                //Map listing to object
                company = objectMapper.readValue(line, Company.class);
            }
        }

        String companyJSON = objectMapper.writeValueAsString(company);
        return companyJSON;
    }

    @Override
    public String createCompany(String str) throws IOException
    {
        String query = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
        rd = communicationController.HttpPostRequest("/api/company?companyAsString=" + query);

        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            return line;
        }
        return "Something Really Bad Happened";
    }

    @Override
    public String updateCompany(String str) throws IOException
    {
        String query = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
        rd = communicationController.HttpPutRequest("/api/company?companyAsString=" + query);

        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            return line;
        }
        return "Something Really Bad Happened";
    }

    @Override
    public String deleteCompanyWish(String str) throws IOException {
        rd = communicationController.HttpPutRequest("/api/company/" + str);

        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            return line;
        }
        return "Something Really Bad Happened";
    }

    @Override
    public String deleteCompany(String str) throws IOException {
        rd = communicationController.HttpDeleteRequest("/api/company/" + str);

        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            return line;
        }
        return "Something Really Bad Happened";
    }

    @Override
    public String getCompaniesToDelete(String str) throws IOException {
        rd = communicationController.HttpGetRequest("/api/company/");

        String line = "";
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Company> companies = new ArrayList<>();
        //Read body
        while ((line = rd.readLine()) != null) {
            if (line != null){
                //Map company to object
                companies = (ArrayList<Company>) objectMapper.readValue(line, new TypeReference<ArrayList<Company>>() {});
            }
        }
        return objectMapper.writeValueAsString(companies);
    }
}
