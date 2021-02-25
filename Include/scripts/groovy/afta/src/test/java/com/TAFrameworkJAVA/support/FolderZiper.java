package com.TAFrameworkJAVA.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * FolderZiper consists add files to zip folder, add file to zip without folder,add folder to zip
 * 
 */
public class FolderZiper {

	/**
	 * addFileToZip will add files to zip folder
	 * 
	 * @param path
	 * @param srcFile
	 * @param zip
	 * @throws Exception
	 * 
	 * 
	 */
	private static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

		File folder = new File(srcFile);

		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		}
		else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
			in.close();
		}
	}

	/**
	 * addFileToZipWithoutFolder will add files to zip without folder
	 * 
	 * @param path
	 * @param srcFile
	 * @param zip
	 * @throws Exception
	 * 
	 * 
	 */
	private static void addFileToZipWithoutFolder(String path, String srcFile, ZipOutputStream zip) throws Exception {

		byte[] buf = new byte[1024];
		int len;

		try {
			FileInputStream in = new FileInputStream(srcFile);

			zip.putNextEntry(new ZipEntry(new File(srcFile).getName()));

			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}

			in.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * addFolderToZip will add folders to zip
	 * 
	 * @param path
	 * @param srcFolder
	 * @param zip
	 * @throws Exception
	 * 
	 * 
	 */
	private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.isEmpty()) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			}
			else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}

	/**
	 * zipFolder will zip the source folder to zip format in the destination
	 * 
	 * @param srcFolders
	 *            - source folder name
	 * @param destZipFile
	 *            - destination zip file name
	 * @throws Exception
	 *             - java exception
	 * 
	 * 
	 */
	public void zipFolder(List <String> srcFolders, String destZipFile) throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);

		for (String srcFolder : srcFolders) {
			if (new File(srcFolder).isDirectory()) {
				addFolderToZip("", srcFolder, zip);
			}
			else {
				addFileToZipWithoutFolder(new File(srcFolder).getName(), srcFolder, zip);
			}

			zip.flush();
		}

		zip.close();
	}
}