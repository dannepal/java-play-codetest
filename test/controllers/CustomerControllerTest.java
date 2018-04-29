package controllers;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Tests form processing.
 *
 * https://www.playframework.com/documentation/latest/JavaFunctionalTest
 */
public class CustomerControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testCustomers() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of(
                    "id", "10000100", "name","customer 100",
                    "duetime", "2018-04-26T12:00:56-07:00", "jointime", "2018-04-26T12:01:56-07:01"))
                .uri("/customers");

        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
    }

}
