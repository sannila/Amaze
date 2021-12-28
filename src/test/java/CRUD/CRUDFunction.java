package CRUD;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Parameters;

public class CRUDFunction {

    public static String base_api = "https://development.api.amazepos.com";


    public String getOrgID(String username, String password){
        RestAssured.baseURI = base_api;
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.post("/user/authenticate");

        JsonPath jsonPathEvaluator = response.jsonPath();
        String orgID = jsonPathEvaluator.get("organizations[\"id\"]").toString();
       return orgID.replace("[", "").replace("]", "");

    }

    /**
     * Ref: https://toolsqa.com/rest-assured/configure-eclipse-with-rest-assured/
     * @param email
     * @return
     */
    public String getUserAPI(String email, int orgId){
        System.out.println("Given emailId: " + email);
        RestAssured.baseURI = base_api;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/user?email=" + email + "&" + "organizationID=" + orgId);
//        JsonPath jsonPathEvaluator = response.jsonPath();
//        String bodyAsString = jsonPathEvaluator.get("email");
//        System.out.println("Actual emailId: " + bodyAsString);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        System.out.println("Body as string:\n\n" + bodyAsString);
        return bodyAsString;
    }



}
