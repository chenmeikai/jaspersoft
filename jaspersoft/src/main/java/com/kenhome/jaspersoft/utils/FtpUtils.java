package com.kenhome.jaspersoft.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 9:59 2018/12/12
 * @Modified By:
 */
public class FtpUtils {
    //ftp服务器地址
    public String hostname = "120.79.35.34";
    //ftp服务器端口号默认为21
    public Integer port = 21;
    //ftp登录账号
    public String username = "css";
    //ftp登录密码
    public String password = "css123";

    public FTPClient ftpClient = null;

    Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            logger.info("connecting ftp:{}:{}", this.hostname, this.port);
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                logger.info("fail to connect  ftp:{}:{}", this.hostname, this.port);
            }
            logger.info("success to connect  ftp:{}:{}", this.hostname, this.port);
        } catch (MalformedURLException e) {
            logger.error("ftp连接异常：", e);
        } catch (IOException e) {
            logger.error("ftp连接异常：", e);
        }
    }

    /**
     * 上传文件
     *
     * @param pathName       ftp服务保存地址
     * @param fileName       上传到ftp的文件名
     * @param originFileName 待上传文件的名称（绝对地址） *
     * @return
     */
    public boolean uploadFile(String pathName, String fileName, String originFileName) {
        boolean flag = true;
        InputStream inputStream = null;
        try {
            logger.info("开始上传文件");
            inputStream = new FileInputStream(new File(originFileName));
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathName);
            ftpClient.makeDirectory(pathName);
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            logger.info("上传文件成功");
        } catch (Exception e) {
            logger.error("上传文件异常:", e);
            flag = false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("ftp断开异常:", e);
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("ftp关闭流异常:", e);
                }
            }
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param pathName    ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile(String pathName, String fileName, InputStream inputStream) {
        boolean flag = true;
        try {
            logger.info("开始上传文件");
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathName);
            ftpClient.makeDirectory(pathName);
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            logger.info("上传文件成功");
        } catch (Exception e) {
            logger.error("上传文件失败", e);
            flag = false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("ftp断开异常:", e);
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("ftp关闭流异常:", e);
                }
            }
        }
        return flag;
    }

    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                logger.info("进入目录:{}成功", directory);

            } else {
                logger.info("进入目录:{}失败", directory);
            }
        } catch (IOException e) {
            logger.error("改变目录路径异常:", e);
            flag = false;
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean flag = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("utf-8"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        logger.info("创建目录[{}]失败", subDirectory);
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return flag;
    }

    //判断ftp服务器文件是否存在
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = false;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                logger.info("创建目录:{}成功", dir);
                flag = true;
            } else {
                logger.info("创建目录:{}失败", dir);
            }
        } catch (Exception e) {
            logger.error("创建目录异常:", e);
        }
        return flag;
    }

    /**
     * 下载文件 *
     *
     * @param pathname  FTP服务器文件目录 *
     * @param filename  文件名称 *
     * @param localpath 下载后的文件路径 *
     * @return
     */
    public boolean downloadFile(String pathname, String filename, String localpath) {
        boolean flag = false;
        OutputStream os = null;
        try {
            logger.info("开始下载文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            logger.info("下载文件成功");
        } catch (Exception e) {
            logger.info("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 删除文件 *
     *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return
     */
    public boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        try {
            logger.info("开始删除文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            logger.info("删除文件成功");
        } catch (Exception e) {
            logger.info("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        FtpUtils ftp = new FtpUtils();
        //ftp.uploadFile("ftpFile/data", "123.docx", "E://123.docx");
        //ftp.downloadFile("ftpFile/data", "123.docx", "F://");
        ftp.deleteFile("ftpFile/data", "123.docx");
    }


}
