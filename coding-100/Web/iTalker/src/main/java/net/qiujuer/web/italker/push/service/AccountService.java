package net.qiujuer.web.italker.push.service;


import net.qiujuer.web.italker.push.bean.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountService  {

//http://localhost:8111/Gradle___iTalker___iTalker_1_0_SNAPSHOT_war/api/account/login
    @GET
    @Path("/login")
    public String get1(){
        return"dljfnvo";
    }

    @Path("/login")
    @POST
    //指定请求与返回的格式为Json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User get2(){
        User user = new User("Alice",0);

        return user;
    }



}
