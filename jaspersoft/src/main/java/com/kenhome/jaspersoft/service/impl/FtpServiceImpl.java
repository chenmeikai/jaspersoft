package com.kenhome.jaspersoft.service.impl;

import com.kenhome.jaspersoft.service.FtpService;
import com.kenhome.jaspersoft.utils.FtpUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 9:38 2018/12/12
 * @Modified By:
 */
@Service
public class FtpServiceImpl implements FtpService {

    @Override
    public boolean exportUser(String userId) {
      FtpUtils ftpUtils =  new FtpUtils();
      ftpUtils.initFtpClient();
      ftpUtils.downloadFile("/","demo.txt","D:\\");
      return true;
    }
}
