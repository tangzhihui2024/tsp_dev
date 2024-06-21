package com.icbcaxa.eata.redis;

import com.icbc.apip.config.Configure;
import com.icbc.apip.context.ApiContext;
import com.icbc.apip.context.SingletoneApiContext;
import com.icbc.apip.exception.InvokerException;
import com.icbc.apip.invoker.DefaultInvoker;
import com.icbc.apip.invoker.Invoker;
import com.icbc.apip.invoker.JsonBuilder;
import com.icbc.apip.token.SignatureAlgo;
import com.icbcaxa.eata.util.constant.CommenConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 唐智慧
 * 2020/6/19.
 * 工行人证比对公共服务
 */
@Component
public class FaceUtil {

    static Logger logger = LoggerFactory.getLogger(FaceUtil.class);

    /**
     * 工行人脸比对
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String, Object> getCheckPicture(Map<String,Object> map) throws Exception {
        Map<String, Object> resp = new HashMap<String,Object>();
        ApiContext context = new SingletoneApiContext();
        Long start = System.currentTimeMillis();
        try {
            // 指定CommenConstant常量(备注：也可以存于配置文件中)，服务配置，设置一次即可（例如：ServletContextListener）
            String async_max_connection=  CommenConstant.ASYNC_MAX_CONNECTIO;
            String socket_timeout=  CommenConstant.SOCKET_TIMEOUT;
            String connection_timeout=  CommenConstant.CONNECTION_TIMEOUT;
            String connection_idle_time=  CommenConstant.CONNECTION_IDLE_TIME;
            String sync_max_connection=  CommenConstant.SYNC_MAX_CONNECTION;
            String async_thread_pool_size=  CommenConstant.ASYNC_THREAD_POOL_SIZE;
            String auth_max_time_step=  CommenConstant.AUTH_MAX_TIME_STEP;
            String default_charset=  CommenConstant.DEFAULT_CHARSET;
            String signature_algo=  CommenConstant.SIGNATURE_ALGO;
            String oauth_api_base_uri=  CommenConstant.OAUTH_API_BASE_URI;
            String oauth_app_id=  CommenConstant.OAUTH_APP_ID;
            String oauth_pubkey_loc =CommenConstant.OAUTH_PUBKEY_LOC+"yourname.pub";
            String oauth_app_prikey_location =CommenConstant.OAUTH_APP_PRIKEY_LOCATION+"yourname.pri";

            logger.info("oauth_pubkey_loc=="+oauth_pubkey_loc);
            logger.info("oauth_app_prikey_location=="+oauth_app_prikey_location);
            Configure configure = new Configure();
            configure.setSysAsyncMaxConnCount(Integer.valueOf(async_max_connection));
            configure.setSysAsyncThreadPoolSize(Integer.valueOf(async_thread_pool_size));
            configure.setSysAuthMaxTimeStep(Long.parseLong(auth_max_time_step));
            configure.setSysBaseUri(oauth_api_base_uri);
            configure.setSysConnIdleTime(Long.parseLong(connection_idle_time));
            configure.setSysConnTimeout(Integer.valueOf(connection_timeout));
            configure.setSysDefaultCharset(default_charset);
            configure.setSysPubkeyLoc(oauth_pubkey_loc);
            configure.setSysSocketTimeout(Integer.valueOf(socket_timeout));
            configure.setSysSyncMaxConnCount(Integer.valueOf(sync_max_connection));
            configure.setAppId(oauth_app_id);
            configure.setUserPrivateLoc(oauth_app_prikey_location);
            configure.setAlgo(SignatureAlgo.valueOf(signature_algo));
            context.setConfigure(configure);
            context.init();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("初始化人脸比对上下文异常",e);
            resp.put("error",e.getMessage());
            resp.put("status", "1");
            return resp;
        }
        DefaultInvoker invoker = new DefaultInvoker();
        invoker.setApiContext(context);
        invoker.setUri(CommenConstant.DICT_VALUE_URI);// 设置请求路径
        invoker.setMethod(Invoker.HttpMethodType.POST);
        String msgid = getMsgId();
        String state = getState();
        String req_json = JsonBuilder.create()
                .add("img1", map.get("img1"))
                .add("img2", map.get("img2"))
                .add("channel", map.get("channel"))
                .add("trCode", map.get("trCode"))
                .add("appName",map.get("appName"))
                .add("appInfo", map.get("appInfo"))
                .add("postMethod", "0")
//              .add("hack1", "1")
//              .add("hack2", "1")
                .add("trxZone", CommenConstant.PROGRAM_NAME)
                .add("customerAgreement",CommenConstant.CUSTOMER_AGREEMENT)
//              .add("programName",remoteProperties.programName)
                .add("id","").build();
        invoker.addParameter("biz_content", req_json);
        try {
            resp  = invoker.syncInvoke();
            // 调用获取结果后的后续业务处理
            Long end =System.currentTimeMillis();
            logger.info("state:"+state);
            logger.info("msgid:"+msgid);
            logger.info("耗时："+(end-start)+"返回resp:" + resp);
            resp.put("state",state);
            resp.put("msgid", msgid);
            resp.put("status", "0");

        }catch (Exception e){
            e.printStackTrace();
            logger.error("调用人脸比对接口异常",e);
            resp.put("error",e.getMessage());
            resp.put("status", "1");
        }finally {
            try {
                if(null !=context){
                    context.shutdown();
                }
            } catch (InvokerException e) {
                logger.error("关闭人脸比对接口上下文异常",e);
                e.printStackTrace();
            }
        }
        return resp;
    }

    /**获取13位随机数
     * @return
     */
    public String getState(){
        final char verifyCodeTable[] = { '0','1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
                'N','O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        int b = verifyCodeTable.length;
        String randomCode ="";
        for (int i = 0; i < 13; ++i) {
            int c = (int) (Math.random() * b);
            char rand = verifyCodeTable[c];
            randomCode += rand;
        }
        return randomCode;
    }

    /**获取当前时间格式为:yyyyMMddHHmmssSSS
     * @return
     */
    public String getMsgId(){
        String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return date;
    }

    /**
     * 清除随机数
     * @param session
     * @param remark
     */
    public void removeRandom(HttpSession session, String remark){
        session.removeAttribute("oauthRandomNumber");
        logger.info(remark+"清楚随机数");
    }



    /**
     *
     * @param plainText
     *            明文
     * @return 32位密文
     */
    public static String cell32(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}
