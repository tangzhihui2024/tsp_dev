package  com.icbcaxa.eata.web.controller;

import com.icbcaxa.eata.entity.Account;
import com.icbcaxa.eata.mapper.AccountMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Log4j
public class OController {


	@Value("${urlSystem}")
	private String urlSystem;

	@Autowired
	private AccountMapper accountMapper;


	@RequestMapping("/hello")
	public  String hello() {

		Account account = accountMapper.selectByPrimaryKey(1017299368463564800L);

		System.out.println("acount name ..."+account != null ? account.getAccountName() :"aaaaaa");

		return urlSystem;

	}
}
