package com.redhat;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;

@Component
public class CamelRoutes extends RouteBuilder {


    @Value("${ansible.tower.url}")
    String ansibleTowerUrl;

	@Value("${trust.store}")
	String trustStore;
	
	@Value("${trust.store.password}")
	String trustStorePassword;	
    
	@Value("${server.address}")	
	String swaggerHost;
	
	@Value("${server.port}")
	String swaggerPort;
	
	@Value("${ansible.api.token}")
	String	ansibleApiToken;
	
    @Bean
    HostnameVerifier hostnameVerifier() {
        return new NoopHostnameVerifier();
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/service/*");
        registration.setName("CamelServlet");
        return registration;
    }
    
    public void configure() throws Exception {

        HttpComponent httpComponent = configureHttp4();
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.auto)
                .producerComponent("http4")
                .apiContextPath("/swagger") //swagger endpoint path
                .apiContextRouteId("swagger") //id of route providing the swagger endpoint
                .contextPath("/service")
                //Swagger properties
                .host("localhost:8087")
                .host(swaggerHost + ":" + swaggerPort)
                .apiProperty("api.title", "Example REST api")
                .apiProperty("api.version", "1.0")
                .apiProperty("api.path", "/service");

           rest()
           		.post("/job_templates/{id}/launch/")
                .route()
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                // Local RHEL
                //.setHeader("Authorization", constant("Bearer IVXJx8n1mKOLq46XpBUtgem5qra4WZ"))
                .setHeader("Authorization", constant("Bearer " + ansibleApiToken))
                .setHeader("Content-Type", constant("application/json"))             
                .to(httpComponent.createEndpoint(ansibleTowerUrl + "?bridgeEndpoint=true"))
                .bean(AnsibleResponseParseBean.class, "getJob");
                System.out.println(ansibleTowerUrl + "?bridgeEndpoint=true");
           rest()
           	.get("/jobs/{id}/")
           	.route()
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            //.setHeader("Authorization", constant("Bearer oFAulfwS6vKa5SVe7F8OH87wE3HOuL"))
            //.setHeader("Authorization", constant("Bearer IVXJx8n1mKOLq46XpBUtgem5qra4WZ"))
            .setHeader("Authorization", constant("Bearer " + ansibleApiToken))
            .setHeader("Content-Type", constant("application/json"))
            .to(httpComponent.createEndpoint(ansibleTowerUrl + "?bridgeEndpoint=true"))
            .bean(AnsibleResponseParseBean.class, "getStatus");
           System.out.println(ansibleTowerUrl + "?bridgeEndpoint=true");
           /*rest()
          	.post("/reboot/")
          	.route()
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.setHeader("Authorization", constant("Basic cGFtQWRtaW46cmVkaGF0cGFtMSE="))
			.setHeader("Content-Type", constant("application/json"))
			.setBody(constant("{\"lookup\":null,\"commands\":[{\"insert\":{\"object\":{\"com.demospace.intelligentinframanagement.rebootDM\":{\"host\":\"h9.com\",\"restart\":null,\"restartedMin\":25,\"restartCount\":3,\"alertOps\":null}},\"out-identifier\":\"rebootDM\"}},{\"set-focus\":{\"name\":\"reboot\"}},{\"fire-all-rules\":{\"out-identifier\":\"firedActivations\"}}]}"))
           .to(configureHttp4NS().createEndpoint("http4://0.0.0.0:8080/kie-server/services/rest/server/containers/instances/IntelligentInfraManagement_1.0.0-SNAPSHOT" + "?bridgeEndpoint=true"))
           .bean(AnsibleResponseParseBean.class, "getStatus1");*/
          
          }

    private HttpComponent configureHttp4() {

        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setResource(trustStore);
        ksp.setPassword(trustStorePassword);
        TrustManagersParameters tmp = new TrustManagersParameters();
        tmp.setKeyStore(ksp);
        SSLContextParameters scp = new SSLContextParameters();
        
        scp.setTrustManagers(tmp);
        HttpComponent httpComponent = getContext().getComponent("https4", HttpComponent.class);
        httpComponent.setSslContextParameters(scp);
        httpComponent.setX509HostnameVerifier(AllowAllHostnameVerifier.INSTANCE);

        return httpComponent;
    }

    private HttpComponent configureHttp4NS() {
        HttpComponent httpComponent = getContext().getComponent("http4", HttpComponent.class);
        return httpComponent;
    }
    
}
