package com.liyuncong.application.deletegithubproject.main;

import java.io.IOException;

/**
 * 
 * @author liyuncong
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Deleter deleter = new SeleniumDeleter();
		deleter.delete();
	}
}
