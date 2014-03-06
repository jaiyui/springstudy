package com.wemakeprice.utility;

public class CheckPrime {

	static final int max = 1000;
	static final int min = 0;
	static int input = 0;

	public void CheckArgs(String arg) throws Exception {
		if (arg == "")
			throw new Exception();
		else {
			Integer num = Integer.valueOf(arg);
			input = num.intValue();

			if (input < 0)
				throw new Exception();
			else if (input > max)
				throw new Exception();
		}
	}

}