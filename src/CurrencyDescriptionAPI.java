/*
 * Author : Harnidh Kaur
 * Date : 11/21/2019
 * Description : This is the driver class of the project which contains the implementation of three REST APIs. Please check the README for more information about running the project.
 */

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class CurrencyDescriptionAPI {

    //Displays welcome text on the default page
    @GET
    public String welcome() {
        return "Welcome to the 'Currency Description by Location' Project which is a simple RESTful API project developed by Harnidh Kaur";
    }

    //Returns the user's current IP address
    @Path("/ip")
    @GET
    public Response getIP() {

        String IP = getIPString();

        if (IP == null) {
            //Return 500 if there is any error in the execution
            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            return responseBuilder
                    .header("status", "failure")
                    .entity("There was an error in execution of the API. Please try after some time.").build();
        }

        //Return successful response if call is working fine
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
        return responseBuilder
                .header("status", "success")
                .entity(IP).build();

    }

    //Takes an IP as Path Param and returns the information of the country from input IP
    @Path("/countryInfo/{ip}")
    @GET
    public Response getCountryInfo(@PathParam("ip") String IP) {

        JsonObject countryInfo = getCountryInfoObject(IP);

        if (countryInfo == null) {

            //Return 400 as error could be due to incorrect input IP
            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST);
            return responseBuilder
                    .header("status", "failure")
                    .entity("There was an error in execution of the API. Please check input and try again.").build();

        }

        //Return successful response if call is working fine
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
        return responseBuilder
                .header("status", "success")
                .entity(countryInfo).build();

    }

    //This method is called if the user does not input any IP in the Path Param
    @Path("/countryInfo")
    @GET
    public Response displayErrorMessage() {

        //Return 400 as we are expecting an input IP in path params
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST);
        return responseBuilder
                .header("status", "failure")
                .entity("Please enter a valid IP address in the path and try again.").build();

    }

    //Returns the description of the currency of the country from where the user is calling
    @Path("/currencyInfo")
    @GET
    public Response getCurrencyInfo() {

        try {

            //Get IP and country information
            String IP = getIPString();
            JsonObject countryInfo = getCountryInfoObject(IP);

            String countryName = countryInfo.getString("name");
            String currencyCode = countryInfo.getString("currency_code");

            //Call the public API to get information about currency
            Client client = ClientBuilder.newClient();
            WebTarget resource = client.target("http://v2.api.forex/infos/currency/" + currencyCode + ".json");
            JsonObject response = resource.request(MediaType.APPLICATION_JSON).get(JsonObject.class);

            JsonObject currencyInfo = response.getJsonObject(currencyCode);
            String currencyName = currencyInfo.getString("name");
            String currencySymbol = currencyInfo.getString("symbol");

            //Build the description
            String description = "You are living in " + countryName + ", where "
                    + " the currency is " + currencyName + ".\n"
                    + "The currency symbol is " + currencySymbol + " and the currency code is " + currencyCode + ".\n";

            //Return successful response if everything works fine
            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
            return responseBuilder
                    .header("status", "success")
                    .entity(description).build();

        } catch (Exception e) {

            //Return 500 if there is any error in the execution
            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            return responseBuilder
                    .header("status", "failure")
                    .entity("There was an error in execution of the API. Please try after some time.").build();

        }

    }


    private String getIPString() {
        try {

            //Call the public API to get the user's IP address
            Client client = ClientBuilder.newClient();
            WebTarget resource = client.target("http://api.ipify.org");
            String response = resource.request(MediaType.APPLICATION_JSON)
                    .get(String.class);
            return response;

        } catch (Exception e) {

            //Returns null if there is any error in execution of the request
            return null;

        }
    }

    private JsonObject getCountryInfoObject(String IP) {

        try {

            //Call the public API to get geolocation information
            Client client = ClientBuilder.newClient();
            WebTarget resource = client.target("http://api.ipgeolocationapi.com/geolocate/" + IP);
            JsonObject response = resource.request(MediaType.APPLICATION_JSON)
                    .get(JsonObject.class);

            return response;

        } catch (Exception e) {

            //Returns null if there is any error in execution of the request
            return null;

        }
    }

}
