package bit.com.a;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bit.com.a.dto.Human;
import bit.com.a.dto.MyClass;

@Controller
public class HelloController {
	private static Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	
	//1번
	@RequestMapping(value="hello.do", method = RequestMethod.GET)
	public String hello(Model model) {
		
		logger.info("HelloController hello()" + new Date());
		
		MyClass cls = new MyClass(1234, "주지훈");
		
		model.addAttribute("mycls", cls);//짐싸
		
		
		return "hello";
	
	
	
	}
	//2번 뷰에서 컨트롤러 전송받음
	@RequestMapping(value="move.do", method = { RequestMethod.GET,RequestMethod.POST})
	public String move(Model model, MyClass mycls) {
		
		logger.info("HelloController move()" + new Date());
		
		
		model.addAttribute("mycls", mycls);
		System.out.println(mycls.toString());
		
		return "hello";
		
	}
	
	//3번 ajax
	@ResponseBody  //ajax 사용시 반드시 사용
	@RequestMapping(value="idcheck.do", method = { RequestMethod.GET,RequestMethod.POST},
					produces = "application/String; charset=utf-8")
	//한국말나오려면 프로덕스
	
	public String idcheck(String id) {
		logger.info("HelloController idcheck()" + new Date());
		
		System.out.println("id="+id);
		
		String str = "오케이";
		
		return str;
		
	}
	
	
	//4번 JSON
	@ResponseBody
	@RequestMapping(value="account.do", method = { RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> account(Human h){
	//Map<String, Object> attribute형태라고 생각
	
		logger.info("HelloController account()" + new Date());
	
	
		System.out.println(h.toString());
		//DB접근
		
		//날려줄거 준비
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "내가보낸 메세지입니다");
		map.put("name", "송중기");
		
		return map;
	
	}
	
	//5번 JSON 리스트로 받기
		@ResponseBody
		@RequestMapping(value="read.do", method = { RequestMethod.GET,RequestMethod.POST})
		public List<MyClass> list(){
		//Map<String, Object> attribute형태라고 생각
		
			logger.info("HelloController list()" + new Date());
		
			System.out.println("list");
			
			//DB접근
			
			//날려줄거 준비
			List<MyClass> list = new ArrayList<MyClass>();
			list.add(new MyClass(456, "원빈"));
			list.add(new MyClass(456, "강동원"));
			
			return list;
		
		}
	
	
	
}//class end
