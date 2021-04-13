package com.sherlock.herokupoc.spring.core;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class RestControllers {
	private static final String ORIGINS = "*";

	@CrossOrigin(origins = ORIGINS)
	@RequestMapping(value = "/logAlert")
	public String logAlert(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "content", required = true) String content) throws Exception {
		Gson gson = new Gson();
		File file = getAlertFile(hostname);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance()
				.getTime());
		String filecontent = timeStamp + "||" + content + System.lineSeparator();
		FileUtils.writeStringToFile(file, filecontent, Charset.defaultCharset()
				.toString(), true);
		return gson.toJson("OK");
	}

	@CrossOrigin(origins = ORIGINS)
	@RequestMapping(value = "/getAlert")
	public String getAlert(@RequestParam(value = "hostname", required = true) String hostname) throws Exception {
		Gson gson = new Gson();
		File file = getAlertFile(hostname);
		return gson.toJson(FileUtils.readFileToString(file, Charset.defaultCharset()
				.toString()));
	}

	private static File getAlertFile(String hostname) {
		return new File("test-temp/", hostname + "alerts.log");
	}

	@CrossOrigin(origins = ORIGINS)
	@RequestMapping(value = "/statusCheck")
	public String getStatusCheck() throws Exception {
		return "OK";
	}

	@CrossOrigin(origins = ORIGINS)
	@RequestMapping(value = "handshake")
	public String handshake() throws Exception {
		return "OK";
	}

	@CrossOrigin(origins = ORIGINS)
	@RequestMapping(value = "status")
	public String status() throws Exception {
		return "OK";
	}

}
