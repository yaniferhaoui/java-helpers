package com.yferhaoui.basic.helper;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public final class ToolHelper {

	public final static boolean Equals(final Exception e1, final Exception e2) {
		return e1.getClass().equals(e2.getClass()) //
				&& StringUtils.equalsIgnoreCase(e1.getMessage(), e2.getMessage()) //
				&& Arrays.deepEquals(e1.getStackTrace(), e2.getStackTrace());
	}
}
