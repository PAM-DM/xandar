package com.redhat;

import java.util.Map;

import org.apache.camel.Exchange;

import com.google.gson.Gson;

public class AnsibleResponseParseBean {

	
	public String getJob(String body) {
		Map<String,String> mapString = new Gson().fromJson(body,Map.class);
		String jobId = new Gson().toJson(mapString.get("job"));
		//System.out.print(jobId.replace(".0", ""));
		return jobId.replace(".0", "");
	}
	
	public String getStatus(String body) {
		Map<String,String> mapString = new Gson().fromJson(body,Map.class);
		String jobStatus = new Gson().toJson(mapString.get("status"));
		//System.out.print(jobStatus.replace("\"", ""));
		return jobStatus.replace("\"", "");
	}
	
	public String getStatus1(String body) {
		Map<String,String> mapString = new Gson().fromJson(body,Map.class);
		String jobStatus = new Gson().toJson(mapString.get("msg"));
		//System.out.print(jobStatus.replace("\"", ""));
		return jobStatus.replace("\"", "");
	}	
	
    public String templateId(Exchange exchange){
        String id = exchange.getIn().getHeader("id").toString();
        return id;
    }	
	
}
