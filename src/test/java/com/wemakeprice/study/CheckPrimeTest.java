package com.wemakeprice.study;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wemakeprice.utility.CheckPrime;

public class CheckPrimeTest {

	private CheckPrime check = new CheckPrime();

	@Test
	public void testCheckArgs() {
		try {
			String arg = "";
			check.CheckArgs(arg);
			fail("예외가 발생해야 함");
		} catch (Exception e) {

		}
	}

}