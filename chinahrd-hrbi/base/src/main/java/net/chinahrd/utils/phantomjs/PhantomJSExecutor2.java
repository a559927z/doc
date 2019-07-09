package net.chinahrd.utils.phantomjs;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhantomJSExecutor2 {

	private final static Logger logger = LoggerFactory.getLogger(PhantomJSExecutor2.class);

	/*** 截图成功后输入 */
	private static final String COMPLETE_OUTPUT = "complete";

	private PhantomJSReference2 phantomJSReference2;

	/**
	 * 截图并返回图片的字节流
	 * 
	 * @param url
	 *            要截图的网页url
	 * @return
	 */
	public byte[] rasterizeAsBytes(String url) {
		PrintArguments2 args = new PrintArguments2();
		args.setUrl(url);
		return rasterizeAsBytes(args);
	}

	/**
	 * 截图并返回图片的字节流
	 * 
	 * @param url
	 *            要截图的网页url
	 */
	public byte[] rasterizeAsBytes(PrintArguments2 args) {
		if (args.getFilename() == null) {
			StringBuilder filename = new StringBuilder();
			filename.append(phantomJSReference2.getOutputDir());
			filename.append(new Date().getTime());
			filename.append('-');
			filename.append(RandomStringUtils.randomAlphabetic(10));
			args.setFilename(filename.toString());
		}

		String output = execute(args.toArgumentsString());
		boolean success = null != output && output.startsWith(COMPLETE_OUTPUT);
		if (false == success && logger.isDebugEnabled()) {
			logger.debug("There may be something wrong when rasterize:'{}'", args.toString());
		}

		File file = new File(args.getFullFilePath());
		byte[] bytes = null;
		try {
			bytes = FileUtils.readFileToByteArray(file);
			file.delete();
		} catch (IOException e) {
			logger.error("Error occurs when rasterize page:{}", args.toString(), e);
		}
		return bytes != null ? bytes : new byte[] {};
	}

	private String execute(String... args) {
		StringBuilder cmd = new StringBuilder();
		cmd.append(phantomJSReference2.getBinaryPath()).append(' ');
		cmd.append(phantomJSReference2.getScriptPath()).append(' ');
		if (args != null) {
			cmd.append(StringUtils.join(args, ' '));
		}
		try {
			final Process process = Runtime.getRuntime().exec(cmd.toString());
			logger.debug("Command to execute:{}", cmd.toString());
			String output = IOUtils.toString(process.getInputStream());
			process.waitFor();
			logger.debug("Output: {}", output);
			return output;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public PhantomJSReference2 getPhantomJSReference() {
		return phantomJSReference2;
	}

	@Autowired
	public void setPhantomJSReference(PhantomJSReference2 phantomJSReference2) {
		this.phantomJSReference2 = phantomJSReference2;
	}

}
