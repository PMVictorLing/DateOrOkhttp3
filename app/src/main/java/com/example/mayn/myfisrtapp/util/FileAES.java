package com.example.mayn.myfisrtapp.util;

import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileAES {
	private static final String TAG = "FileAES";
	private static FileAES m_Instance = null;
	//默认密钥向量
//	private static byte[] AES_IV = { 0x12, 0x34, 0x56, 0x78, (byte)0x90, (byte)0xAB, (byte)0xCD, (byte)0xEF, 0x12, 0x34, 0x56, 0x78, (byte)0x90, (byte)0xAB, (byte)0xCD, (byte)0xEF };
	//向量 string aesiv = "2019010718065687"; AES/CBC/PKCS5Padding
	private static String AES_IV = "2019010718065687";

	private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS7Padding";

	private static final int REVERSE_LENGTH = 16;


  //生成key
	public static  String getMiKey(String userId){
		String sub = userId.substring(2,8);
//		String subString= sub.replace("-","");
		String  aa = sub.toLowerCase();
		String str = aa + "botast2019";
		return str;
	}



	// 解密
	public static String decrypt(String strCipherText, String strKey) {


		if (TextUtils.isEmpty(strCipherText)) {
			return null;
		}

		if (null == strKey) {
			return null;
		}

		// check the KEY is 16 or not
//		if (16 != strKey.length()) {
//			return null;
//		}

		try {
//			strCipherText = strCipherText.replace(' ','+');
			byte[] raw = strKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
			IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] cipherText = org.apache.commons.net.util.Base64.decodeBase64(strCipherText); // decode by BASE64 first


			byte[] clearText = cipher.doFinal(cipherText);

			String strClearText = new String(clearText,"UTF-8");

			return strClearText;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	public static byte[] decrypt(byte[] bCipherText, String strKey) {
		if (TextUtils.isEmpty(strKey)) {
			return null;
		}

		if (null == bCipherText) {
			return null;
		}

		// check the KEY is 16 or not
//		if (16 != strKey.length()) {
//			return null;
//		}

		try {
//			strCipherText = strCipherText.replace(' ','+');
			byte[] raw = strKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
			IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

		//	byte[] cipherText = Base64.decodeBase64(bCipherText); // decode by BASE64 first


			byte[] clearText = cipher.doFinal(bCipherText);

			return clearText;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	private static String getAvailablePath(String path) {
		String strAvailablePath = path;
		if ((path == null) || (path.length() < 1)) {
			return strAvailablePath;
		}
		String mStrRootPath = getExternalStorageDirectory();
		if (!strAvailablePath.startsWith(mStrRootPath)) {
			strAvailablePath = mStrRootPath+"/"+path;
		}

		return strAvailablePath;
	}

	private static byte[] readAsFile32Bytes(String filePath) {
		byte[] buffer = new byte[32];
		filePath = getAvailablePath(filePath);
		try {
			File f = new File(filePath);
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			if (raf.length() >= 32) {
				raf.read(buffer,0,32);
			}
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer;
	}

	public static boolean rename(String sourceNamePath, String newFilePath) {
		boolean bRet = false;
		String filePath = getAvailablePath(sourceNamePath);
		File storeFile = new File(filePath);

		String filePathNew = getAvailablePath(newFilePath);

		bRet = storeFile.renameTo(new File(filePathNew));

		return bRet;
	}

	private static boolean modifyFileFrom16Bytes(String filePath) {
		boolean bRet = false;
		try {
			String filePathNew = filePath+".k";
			if (rename(filePath,filePathNew)) {

				File f = new File(filePathNew);
//				RandomAccessFile raf = new RandomAccessFile(f, "rw");
//				if (raf.length() >= 32) {
//					raf.seek(16);
//				}

				InputStream inStream = new FileInputStream(f);

				FileOutputStream outStream = new FileOutputStream(new File(filePath));

				byte[] buffer = new byte[1024];

				int len = -1;

				if ( (len = inStream.read(buffer,16,16)) != -1 ) {

					outStream.write(buffer, 0, len);

				}

				while( (len = inStream.read(buffer)) != -1 ){

					outStream.write(buffer, 0, len);

				}

				if (outStream != null) {
					try {
						outStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				f.delete();

				bRet = true;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bRet;
	}

	/**
	 * 获取U盘或是sd卡的路径
	 * @return
	 */
	public static String getExternalStorageDirectory(){

		String dir = new String();

		try {
			File file = new File("/mnt/sdcard");
			if(file.exists() && file.isDirectory()){
				dir = "/mnt/sdcard";
				return dir;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure")) continue;
				if (line.contains("asec")) continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						dir = columns[1] ;
					}
				}
			}

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dir;
	}

}
