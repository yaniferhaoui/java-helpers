package com.yferhaoui.basic.helper;

import java.io.File;

public final class FileHelper {

	public final static File setPath(final File file) {
		file.getParentFile().mkdirs();
		return file;
	}

	public final static String getProjectDirectory() {
		return System.getProperty("user.dir") + File.separator;
	}

	public final static String getWorkspaceDirectory() {
		return new File(System.getProperty("user.dir")).getParent() + File.separator;
	}
}
