import com.alibaba.fastjson.JSONObject;
import com.icbcaxa.eata.ServiceApplication;
import com.icbcaxa.eata.service.EataLiveSctagCertService;
import com.icbcaxa.eata.util.AESUtil;
import com.icbcaxa.eata.util.dto.EATARequest;
import com.icbcaxa.eata.util.dto.Head;
import com.icbcaxa.eata.vo.EataLiveSctagCertVo;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by gyas-itwb-fsl01 on 2020/3/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})
public class EataLiveSctagCertControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EataLiveSctagCertService eataLiveSctagCertServiceImpl;

    //使用mockBaen覆盖掉拦截器配置
    private final static String USER_ID = "20000122";
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void faceKernelAuth() throws Exception{
        EATARequest<EataLiveSctagCertVo> eataRequest=new EATARequest<>();
        EataLiveSctagCertVo eataLiveSctagCertVo = new EataLiveSctagCertVo();
        eataLiveSctagCertVo.setCallSystem("dsfd");
        eataLiveSctagCertVo.setIdentifier(USER_ID);
        Head head=new Head();
        head.setUserId(USER_ID);
        eataRequest.setHead(head);
        eataRequest.setBody(eataLiveSctagCertVo);
        String result = this.mockMvc.perform(
                post("/eata/sctag/query")
                        .contentType("application/json;charset=UTF-8")
                        .content(JSONObject.toJSON(eataRequest).toString())
        ).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONObject response = JSONObject.parseObject(result);

        String encrpyData = (String)response.get("body");
        if(!StringUtils.isEmpty(encrpyData)) {
            System.out.println(AESUtil.aesDecrypt(encrpyData, AESUtil.getEncodeKey()));
        }
    }

}
