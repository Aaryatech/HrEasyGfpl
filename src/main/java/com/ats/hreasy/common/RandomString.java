package com.ats.hreasy.common;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomString {

	public static final String upper = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String digits = "0123456789";
	public static final String special = "@#!$";
	public static final String alphanum = upper + digits;
	private final Random random;
	private final char[] symbols;
	private final char[] digSy;
	private final char[] spciSy;
	private final char[] buf;

	public String nextString() {

		List<String> answersList = new ArrayList<>();
		String finalString = new String();

		buf[0] = digSy[random.nextInt(digits.length())];
		buf[1] = spciSy[random.nextInt(special.length())];
		answersList.add(String.valueOf(buf[0]));
		answersList.add(String.valueOf(buf[1]));

		// System.out.println(buf);
		for (int idx = 2; idx < buf.length; ++idx) {
			buf[idx] = symbols[random.nextInt(upper.length())];
			answersList.add(String.valueOf(buf[idx]));
		}

		Collections.shuffle(answersList);

		for (int i = 0; i < answersList.size(); i++) {
			finalString = finalString + answersList.get(i);
		}
		return finalString;
	}

	public RandomString(int length, Random random, String symbols, String digSy, String spciSy) {
		if (length < 1)
			throw new IllegalArgumentException();
		if (symbols.length() < 2)
			throw new IllegalArgumentException();
		this.random = Objects.requireNonNull(random);
		this.symbols = symbols.toCharArray();
		this.digSy = digSy.toCharArray();
		this.spciSy = spciSy.toCharArray();
		this.buf = new char[length];
	}

	/**
	 * Create an alphanumeric string generator.
	 */
	public RandomString(int length, Random random) {
		this(length, random, upper, digits, special);
	}

	/**
	 * Create an alphanumeric strings from a secure generator.
	 */
	public RandomString(int length) {
		this(length, new SecureRandom());
	}

	/**
	 * Create session identifiers.
	 */
	public RandomString() {
		this(8);
	}
}
