package com.assess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assess.entities.CodeEntry;
import com.assess.entities.Result;
import com.assess.services.CodeExecutor;

@RestController
@RequestMapping("/api/code")
public class CodeReceiver {

	@Autowired
	CodeExecutor executor;
	@PostMapping()
	public Result processCode(@RequestBody CodeEntry code) throws Exception {
	   return executor.compileAndExecute(code.getCode() , code.getPersonName(),code.getCodeClass());
	  
	}
}
