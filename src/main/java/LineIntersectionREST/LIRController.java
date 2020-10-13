package LineIntersectionREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LIRController {
	
		   @Autowired(required=true)
		   UPService fileService;

		   @RequestMapping(value = "/")	
		    public String index1() {
		        return "upload";
		    }

		    @PostMapping("/uploadFile")
		    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		        fileService.uploadFile(file);

		        redirectAttributes.addFlashAttribute("message",
		            "You successfully uploaded " + file.getOriginalFilename() + "!");

		        return "redirect:/";
		    }				  
}
