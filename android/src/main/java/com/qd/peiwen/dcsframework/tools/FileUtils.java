package com.qd.peiwen.dcsframework.tools;

import android.text.TextUtils;
import android.util.Base64;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

/**
 * Created by jeffreyliu on 16/11/8.
 */

public class FileUtils {

    public static final long GB = 1024 * 1024 * 1024;//定义GB的计算常量
    public static final long MB = 1024 * 1024;//定义MB的计算常量
    public static final long KB = 1024;//定义KB的计算常量

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        return new File(filePath).exists();
    }

    public static boolean createFolder(String filePath) {
        File folder = new File(filePath);
        if (folder.exists()) {
            return true;
        } else {
            return folder.mkdirs();
        }
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    public static String getFileParentDirectory(String filepath) {
        if (null == filepath) {
            return null;
        } else {
            return getFileParentDirectory(new File(filepath));
        }
    }


    public static String getFileParentDirectory(File file) {
        if (null == file) {
            return null;
        } else {
            File parent = file.getParentFile();
            if (null == parent) {
                return null;
            } else {
                return parent.getName();
            }
        }
    }

    public static long getFileSize(String filepath) {
        if (TextUtils.isEmpty(filepath)) {
            return -1;
        }
        return getFileSize(new File(filepath));
    }

    public static long getFileSize(File file) {
        if (null == file) {
            return -1;
        }
        return ((file.exists() && file.isFile()) ? file.length() : 0);
    }

    public static String formatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#0.00");

        String unit = "B";
        double unitLegth = fileS;
        if (fileS / GB >= 1) {//如果当前Byte的值大于等于1GB
            unit = "GB";
            unitLegth = 1.0 * fileS / GB;
        } else if (fileS / MB >= 1) {//如果当前Byte的值大于等于1MB
            if (fileS / MB >= 1000) {
                unit = "GB";
                unitLegth = 1.0 * fileS / GB;
            } else {
                unit = "MB";
                unitLegth = 1.0 * fileS / MB;
            }
        } else if (fileS / KB >= 1) {//如果当前Byte的值大于等于1KB
            if (fileS / KB >= 1000) {
                unit = "MB";
                unitLegth = 1.0 * fileS / MB;
            } else {
                unit = "KB";
                unitLegth = 1.0f * fileS / KB;
            }
        } else {
            if (fileS >= 1000) {
                unit = "KB";
                unitLegth = 1.0f * fileS / KB;
            }
        }
        return (df.format(unitLegth) + unit);
    }


    public static boolean renameFile(String filepath, String newName) {
        File file = new File(filepath);
        return file.exists() && file.renameTo(new File(newName));
    }

    public static void deleteFile(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            deleteFile(new File(filename));
        }
    }

    public static void deleteFile(File file) {
        if (null != file && file.exists()) {
            file.delete();
        }
    }

    public static boolean deleteDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (File file : files) {
                    deleteFile(file);
                }
            }
            return directory.delete();
        }
        return true;
    }

    public static boolean writeFile(String path, byte[] bytes, int length) {
        RandomAccessFile raf = null;
        try {
            File file = new File(path);
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(bytes, 0, length);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean insertFile(String path, byte[] bytes, int pos, int length) {
        RandomAccessFile raf = null;
        try {
            File file = new File(path);
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(pos);
            raf.write(bytes, 0, length);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File saveFile(String content, String path) {
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(path);
            fwriter.write(content);
            LogUtils.d("module>> save cache to " + path);
        } catch (IOException ex) {
            LogUtils.e("saveFile error " + ex.getMessage());
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                LogUtils.e("saveFile error " + ex.getMessage());
            }
        }
        return null;
    }

    public static String readFile(String path) {
        BufferedReader bf = null;
        try {
            File file = new File(path);
            bf = new BufferedReader(new FileReader(file));
            String content = "";
            StringBuilder sb = new StringBuilder();
            while (content != null) {
                content = bf.readLine();
                if (content == null) {
                    break;
                }
                sb.append(content.trim());
            }
            return sb.toString();
        } catch (IOException e) {
            LogUtils.e("readFile error: " + e.getMessage());
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    LogUtils.e("readFile error " + e.getMessage());
                }
            }
        }
        return null;
    }


    public static String file2Base64(String filepath, long from, long length) {
        return file2Base64(new File(filepath), from, length);
    }

    public static String file2Base64(File file, long from, long length) {
        if (null == file || file.length() == 0) {
            return null;
        }
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            inputFile.skip(from);
            if(length == 0){
                length = file.length() - from;
            }
            byte[] buffer = new byte[(int)length];
            inputFile.read(buffer);
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputFile) {
                    inputFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
