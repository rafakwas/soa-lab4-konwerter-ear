package com.ralphigi.ejb.remote.stateless;

import javax.ejb.Stateless;

@Stateless(name = "HelloEJB")
public class HelloBean {

    public String sayHello(String name) {
        return "Hello " + name;
    }

}
