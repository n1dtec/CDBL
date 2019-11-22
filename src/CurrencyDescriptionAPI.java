import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CurrencyDescriptionAPI {

    @GET
    public String welcome(){
        return "Welcome to the 'Currency Description by Location' Project developed which is a simple RESTful API project developed by Harnidh Kaur";
    }

    @Path("/ip")
    @GET
    public String getIP(){

        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://api.ipify.org");
        String response = resource.request(MediaType.APPLICATION_JSON)
                //.header("IP Address", )
                .get(String.class);

        return response;

    }

    @Path("/currencyCode")
    @GET
    public JsonObject getCountryInfo(){

        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://api.ipgeolocationapi.com/geolocate/" + getIP());
        JsonObject response = resource.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        return response;

    }

    @Path("/currencyInfo")
    @GET
    public String getCurrencyInfo(){

        JsonObject countryInfo = getCountryInfo();

        String countryName = countryInfo.getString("name");
        String currencyCode = countryInfo.getString("currency_code");

        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://v2.api.forex/infos/currency/" + currencyCode + ".json");
        JsonObject response = resource.request(MediaType.APPLICATION_JSON).get(JsonObject.class);

        JsonObject currencyInfo = response.getJsonObject(currencyCode);
        String currencyName = currencyInfo.getString("name");
        String currencySymbol = currencyInfo.getString("symbol");

        String description = "You are living in " + countryName + " where "
                + " the currency is " + currencyName + ".\n"
                + "The currency symbol is " + currencySymbol + " and the currency code is " + currencyCode + ".\n";

        return description;

    }

}
