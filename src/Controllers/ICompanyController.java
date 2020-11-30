package Controllers;

import java.io.IOException;

public interface ICompanyController {

    String getCompany(String id) throws IOException;
    String createCompany(String str) throws IOException;
    String updateCompany(String s) throws  IOException;
    String deleteCompanyWish(String str) throws IOException;
    String deleteCompany(String str) throws IOException;
    String getCompaniesToDelete(String str) throws IOException;
}
