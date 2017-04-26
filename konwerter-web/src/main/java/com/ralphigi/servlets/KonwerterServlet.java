package com.ralphigi.servlets;

import com.ralphigi.datatypes.ConversionDirection;
import com.ralphigi.ejb.remote.stateful.Konwerter;
import com.ralphigi.ejb.remote.stateful.KonwerterBean;

import javax.naming.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

@javax.servlet.annotation.WebServlet(name = "KonwerterServlet", urlPatterns = {"konwerterServlet"})
public class KonwerterServlet extends javax.servlet.http.HttpServlet {
    private double temperature;
    private ConversionDirection conversionDirection;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>Witam administratora, jak zdrówko?</h1>");

        try {
            temperature = Double.parseDouble(request.getParameter("temperature"));
        } catch (NumberFormatException e) {

        }

        switch (request.getParameter("direction")) {
            case "ftoc" :
                conversionDirection = ConversionDirection.FTOC;
                break;
            case "ctof" :
                conversionDirection = ConversionDirection.CTOF;
                break;
            default:
                throw new RuntimeException("Direction unknown");
        }

        out.println("Temperature given: " + temperature);
        out.println("<br>Conversion direction: " + conversionDirection);

        Konwerter konwerter = null;
        try {
            konwerter = lookupRemoteStatefulKonwerter();
        }catch (NamingException e) {
            e.printStackTrace();
            out.println("<br>Błąd jndi: " + e.getMessage());
        }

        double result = konwerter.getTemperature(temperature,conversionDirection);

        out.println("<br>Conversion result ("+temperature+","+conversionDirection+") is " + result);
        out.close();
    }

    private static Konwerter lookupRemoteStatefulKonwerter() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        // The JNDI lookup name for a stateless session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        // (without the .ear). If the EJB JAR is not deployed in an EAR then this is
        // blank. The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        // .jar suffix). The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : AS7 allows each deployment to have an (optional) distinct name.
        // This example does not use this so leave it blank.
        //
        // <beanName> : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface. Must include
        // the whole package name.


        // let's do the lookup
        Konwerter remoteKonwerter = (Konwerter) context.lookup("java:app/konwerter-ejb/KonwerterEJB");
        context.close();
        return remoteKonwerter;
    }
}
