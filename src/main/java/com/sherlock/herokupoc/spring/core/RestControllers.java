package com.sherlock.herokupoc.spring.core;

import java.io.File;
import java.nio.charset.Charset;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class RestControllers {
	private static final String ORIGINS = "*";

	@CrossOrigin(origins = ORIGINS)
	@GetMapping(value = "/logAlert")
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
	@GetMapping(value = "/getAlert")
	public String getAlert(@RequestParam(value = "hostname", required = true) String hostname) throws Exception {
		Gson gson = new Gson();
		File file = getAlertFile(hostname);
		return gson.toJson(FileUtils.readFileToString(file, Charset.defaultCharset()
				.toString()));
	}

	@CrossOrigin(origins = ORIGINS)
	@GetMapping(value = "/space")
	public String space() throws Exception {
		Gson gson = new Gson();
		String contextPath = System.getProperty("user.dir");
		File contextDirectory = new File(contextPath);
		return gson.toJson(calculateAvailableSpace(contextDirectory));
	}

	@CrossOrigin(origins = ORIGINS)
	@GetMapping(value = "/statusCheck")
	public String getStatusCheck() throws Exception {
		return "OK";
	}

	@CrossOrigin(origins = ORIGINS)
	@GetMapping(value = "handshake")
	public String handshake() throws Exception {
		return "OK";
	}

	@CrossOrigin(origins = ORIGINS)
	@GetMapping(value = "status")
	public String status() throws Exception {
		return "OK";
	}

	/*
	 * UTILS
	 */
	private static File getAlertFile(String hostname) {
		return new File("test-temp/", hostname + "alerts.log");
	}

	private static String calculateAvailableSpace(File targetLocation) throws Exception {
		long usableSpace = targetLocation.getUsableSpace();
		long totalSpace = targetLocation.getTotalSpace();

		return humanReadableByteCountBin(usableSpace) + " / " + humanReadableByteCountBin(totalSpace);
	}

	private static String humanReadableByteCountBin(long bytes) {
		long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
		if (absB < 1024) {
			return bytes + " B";
		}
		long value = absB;
		CharacterIterator ci = new StringCharacterIterator("KMGTPE");
		for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
			value >>= 10;
			ci.next();
		}
		value *= Long.signum(bytes);
		return String.format("%.3f %ciB", value / 1024.0, ci.current());
	}

}
