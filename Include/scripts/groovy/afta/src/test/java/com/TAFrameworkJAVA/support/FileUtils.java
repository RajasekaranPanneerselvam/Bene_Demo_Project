package com.TAFrameworkJAVA.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * FileUtils consists copy/move a file from source to destination location
 */
public class FileUtils {

	/**
	 * Copy a file from one location to another
	 * 
	 * @param f1
	 *            - Source file
	 * @param f2
	 *            - Destination File
	 * @throws IOException
	 *             - java IO exception
	 */
	public static void copyFile(File f1, File f2) throws IOException {
		InputStream in = new FileInputStream(f1);

		// For Overwrite the file.
		OutputStream out = new FileOutputStream(f2);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/***
	 * Method move the file from source location, rename it and place it in the destination location and deletes the old file from the destination location if any
	 * 
	 * @param oldFile
	 *            - source file name
	 * @param newFile
	 *            - destination file name
	 * @throws IOException
	 *             - java IO exception
	 */
	public static void moveFile(String oldFile, String newFile) throws IOException {
		File oldfile = new File(oldFile);
		File newfile = new File(newFile);
		copyFile(oldfile, newfile);
		oldfile.delete();
	}
}
