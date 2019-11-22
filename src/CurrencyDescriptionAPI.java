/*
 * Author : Harnidh Kaur
 * Date : 11/21/2019
 * Description : This is the driver class of the project which contains the implementation of three REST APIs. Please check the README for more information about running the project.
 */

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CurrencyDescriptionAPI {

    //Displays welcome text on the default page
    @GET
    public String welcome(){
        return "Welcome to the 'Currency Description by Location' Project which is a simple RESTful API project developed by Harnidh Kaur";
    }

    //Returns the user's current IP address
    @Path("/ip")
    @GET
    public String getIP(){

        //Call the public API to get the user's IP address
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://api.ipify.org");
        String response = resource.request(MediaType.APPLICATION_JSON)
                //.header("IP Address", )
                .get(String.class);

        return response;

    }

    //Returns the information of the country from where the user is calling
    @Path("/countryInfo")
    @GET
    public JsonObject getCountryInfo(){

        //Call the public API to get geolocation information
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://api.ipgeolocationapi.com/geolocate/" + getIP());
        JsonObject response = resource.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        return response;

    }

    //Returns the description of the currency of the country from where the user is calling
    @Path("/currencyInfo")
    @GET
    public String getCurrencyInfo(){

        JsonObject countryInfo = getCountryInfo();

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
        String description = "You are living in " + countryName + " where "
                + " the currency is " + currencyName + ".\n"
                + "The currency symbol is " + currencySymbol + " and the currency code is " + currencyCode + ".\n";

        return description;

    }

}
