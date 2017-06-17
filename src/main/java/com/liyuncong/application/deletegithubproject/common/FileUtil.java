package com.liyuncong.application.deletegithubproject.common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 
 * @author liyuncong
 *
 */
public class FileUtil {
	public static List<String> readAllLines(String pathName) throws IOException {
		Path path = Paths.get(pathName);
		return Files.readAllLines(path, Charset.forName(Constants.FROM_CHARSET));
	}
}
