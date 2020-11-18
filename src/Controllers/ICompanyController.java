package Controllers;

import java.io.IOException;

public interface ICompanyController {

    String getCompany(String id) throws IOException;
    String createCompany(String str) throws IOException;
    //getAllCompanies...
    //getCompanyId? //TODO
    //TODO
}
