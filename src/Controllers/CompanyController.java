package Controllers;

import Models.Company;
import Models.Listing;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
}
