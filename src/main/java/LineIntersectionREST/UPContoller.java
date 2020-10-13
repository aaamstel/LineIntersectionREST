package LineIntersectionREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public class UPContoller {

	   @Autowired
	   UPService fileService;

	    @GetMapping("/")
	    public String index() {
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
