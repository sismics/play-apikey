package controllers.interceptor;

import play.mvc.After;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;
import play.utils.Java;

import java.lang.reflect.InvocationTargetException;

/**
 * @author jtremeaux
 */
public class ApiKeySecure extends Controller {
    @Before
    static void checkAccess() throws Throwable {
        CheckApiKey checkApiKey = getActionAnnotation(CheckApiKey.class);
        if (checkApiKey == null) {
            return;
        }
        Http.Header apiKeyHeader = request.headers.get("authorization");
        if (apiKeyHeader == null) {
            unauthorized("Please specify Authorization header");
        }
        String apiKeyHeaderValue = apiKeyHeader.value();
        if (!apiKeyHeaderValue.startsWith("Token ")) {
            unauthorized("The Authorization must have the value 'Token XXX'");
        }
        String apiKey = apiKeyHeaderValue.substring("Token ".length());

        // Check API Key
        String id = (String) Security.invoke("authenticate", apiKey);
        if (id == null) {
            unauthorized("Invalid API key");
        }
    }

    @Before
    public static void before() throws Throwable {
        Security.invoke("before");
    }

    @After
    public static void after() throws Throwable {
        Security.invoke("after");
    }

    public static class Security extends Controller {
        /**
         * This method is called during the authentication process. This is where you check if
         * the API keys allows to log in into the system. This is the actual authentication process
         * against a third party system (most of the time a DB).
         *
         * @param apiKey The API Key
         * @return Entity ID if the authentication process succeeded
         */
        static String authenticate(String apiKey) {
            return null;
        }

        /**
         * This method is called before before all actions.
         */
        static void before() {
        }

        /**
         * This method is called before after all actions.
         */
        static void after() {
        }

        private static Object invoke(String m, Object... args) throws Throwable {
            try {
                return Java.invokeChildOrStatic(Security.class, m, args);
            } catch(InvocationTargetException e) {
                throw e.getTargetException();
            }
        }

    }
}
