package controllers;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.Environment;
import play.libs.Json;
import play.mvc.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

public class CustomerController extends Controller {
    private final Environment env;
    List<Customer> sortedcustomers = new ArrayList<Customer>();

    @Inject
    public CustomerController(final Environment env) {
        // the environment is used to access local files
        this.env = env;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result customers() {
        JsonNode json = request().body().asJson();
        if(json == null) {
            File file = new File("conf/customers.json");

            try (FileInputStream fis = new FileInputStream(file);) {
                json = Json.parse(fis);
            } catch(IOException e){
                return internalServerError("Something went wrong");
            }
        }

        List<Customer> customers = new ArrayList<Customer>();
        List<DateTime> dues = new ArrayList<DateTime>();
        Customer customer = null;

        ArrayNode results = (ArrayNode)json;
        Iterator<JsonNode> it = results.iterator();

        while (it.hasNext()) {
            JsonNode node  = it.next();
            dues.add(new DateTime(node.get("duetime").asText()));
            customer = new Customer(
                new Long(node.get("id").asText()), node.get("name").asText(),
                node.get("duetime").asText(), node.get("jointime").asText());
            customers.add(customer);
        }

        // sort duetimes
        Collections.sort(dues);
        for(DateTime duetime : dues) {
          for(Customer c : customers) {
                if( c.duetime != null && !c.duetime.equals("") &&
                      duetime.isEqual(new DateTime(c.duetime)) ) {
                    this.sortedcustomers.add(c);
                    break;
                }
            }
        }

        json = Json.toJson(this.sortedcustomers);
        return ok(json);
        //return ok(views.html.customers.render(asScala(this.sortedcustomers)));
    }

}
