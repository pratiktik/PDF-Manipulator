package com.codeplanet.controller;


import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFBoxTree;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.codeplanet.dao.HomeDao;
import com.codeplanet.model.User;
import com.codeplanet.model.UserFeed;
import com.codeplanet.model.UserFile;
import com.codeplanet.service.HomeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
/**
 * @author shubhamkumar
 *
 */
/**
 * @author shubhamkumar
 *
 */
/**
 * @author shubhamkumar
 *
 */
@Controller
public class HomeController {
	@Autowired
	HomeService homeService;
	
	
	 
@RequestMapping(value="/")
	public String home(UserFeed userfeed,HttpServletRequest req) throws FileNotFoundException, SQLException{
	
	
	ArrayList<UserFeed> l= homeService.getInfo1();
	req.setAttribute("xyz",l);
		System.out.println("controller called");
		return "index";
	}

@RequestMapping(value="/merge")
	public String merge() {
		return "index1";
	}


	
	
@RequestMapping(value="/mergepdf",method=RequestMethod.POST)
	public String mergepdf(HttpServletRequest req,UserFile user)throws SQLException,IOException {
		
		ArrayList<String> filepath=uploadFileOnServer(user);
		merger(filepath,req);
		System.out.println("pdf merged successfully");
		return "downloadmerged";
		
	}
	
	
		public void merger(ArrayList<String> filepath, HttpServletRequest req) throws IOException {
		PDFMergerUtility pdf=new PDFMergerUtility();
		pdf.setDestinationFileName("/Users/shubhamkumar/Desktop/files/merge"+ 5 +".pdf");
		for(String s:filepath)
		{
			File f=new File(s);
			pdf.addSource(f);
		}
		pdf.mergeDocuments(null);
		System.out.println("pdf merging");
		req.setAttribute("file","/Users/shubhamkumar/Desktop/files/merge5.pdf" );		
        }
	
		
		private ArrayList<String> uploadFileOnServer(UserFile user)  {
		String rootdirectory="/Users/shubhamkumar/Desktop/files/merge";
		File directory=new File(rootdirectory);
		if(!directory.exists())
			directory.mkdirs();
		MultipartFile[] f=user.getUserfiles();
		String filepath=null;
		ArrayList<String> list=new ArrayList<String>();
		for(MultipartFile filedata : f) {
		String filename=filedata.getOriginalFilename();
		if(filename!=null && filename.length()>0)
		{
			try {
				filepath=directory.getCanonicalPath()+File.separator+filename;
				BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath));
				bos.write(filedata.getBytes());
				bos.close();
				list.add(filepath);
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}		
		}
		System.out.println("pdf uploaded on server");
		
			return list;
	}

	
	
	
@RequestMapping(value="mergedownload",method=RequestMethod.POST)
	public String download(HttpServletRequest req,HttpServletResponse res) throws IOException {
	String mimetype=null;
	String file=req.getParameter("filepath");
	File f=new File(file);
	mimetype=getMimeType(f.getCanonicalPath());
	res.setContentType(mimetype);
	res.setHeader("content-Disposition", "attachment;filename=\""+f.getName()+"\"");
	res.setContentLength((int)f.length());
	InputStream is=new FileInputStream(f);
	ServletOutputStream out=res.getOutputStream();
	
	IOUtils.copy(is, out);
	out.flush();
	out.close();
	is.close();
	return "index";
}


	private String getMimeType(String canonicalPath) {
		canonicalPath=canonicalPath.toLowerCase();
		if(canonicalPath.endsWith(".jpg")||canonicalPath.endsWith(".jpeg")||canonicalPath.endsWith(".png"))
			return "image/jpeg";
		else if(canonicalPath.endsWith(".pdf"))
			return "application/pdf";
		else
			return "application/pdf";	
		}	

//merging complete
//splitter code starting
	
	@RequestMapping(value="/split")
	public String split() {
		return "index2";
	}
	
	@RequestMapping(value="splitpdf",method=RequestMethod.POST, produces = "application/zip")
	public String splitpdf(HttpServletRequest req,UserFile user,HttpServletResponse res)throws SQLException,IOException {
	
		String filepath=uploadFileOnServer1(user);
		
		splitter(filepath,req,res);
		System.out.println("pdf SPLITTED successfully");
		
		return "downloadsplitted";
	}

	
	
	private String uploadFileOnServer1(UserFile user) {
	
		String rootdirectory="/Users/shubhamkumar/Desktop/files/split";
		File directory=new File(rootdirectory);
		if(!directory.exists())
			directory.mkdirs();
		MultipartFile f=user.getUserfile();
		String filepath=null;	
		String filename=f.getOriginalFilename();
		if(filename!=null && filename.length()>0)
		{
			try {
				filepath=directory.getCanonicalPath()+File.separator+filename;
				BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath));
				bos.write(f.getBytes());
				bos.close();		
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		System.out.println("pdf uploaded on server");
		
		
		return filepath;
    }

	@RequestMapping(value="/splittzipdownload")
	public String splitter(String filepath, HttpServletRequest req,HttpServletResponse res) throws IOException {
		
		File file=new File(filepath);
		PDDocument pd=PDDocument.load(file);
		
		
		Splitter sp=new Splitter();
		
		List<PDDocument> pd1=sp.split(pd);
		Iterator<PDDocument> it=pd1.listIterator();
		int i=1;
		List<String> filepaths=new ArrayList<String>();
		
		while(it.hasNext())
		{
			
			String x="/Users/shubhamkumar/Desktop/files/split/split"+i+".pdf";
			PDDocument pd2=it.next();
			pd2.save(x);
			i++;	
			filepaths.add(x);
		}
		pd.close();
		zipFiles(filepaths,res);
		return "index";	
	}
	public void zipFiles(List<String> filepaths, HttpServletResponse res) throws IOException {
		String zipFileName="/Users/shubhamkumar/Desktop/splitterzip.zip";
		FileOutputStream fos= new FileOutputStream(zipFileName);
		ZipOutputStream zos= new ZipOutputStream(fos);
		for(String s:filepaths)
		{
			zos.putNextEntry(new ZipEntry(new File(s).getName()));
			byte[] bytes=Files.readAllBytes(Paths.get(s));
			zos.write(bytes);
			zos.closeEntry();			
		}
		zos.close();
		File f= new File(zipFileName);
		res.setContentType("application/zip");
		res.setHeader("Content-Disposition", "attachement;filename=\""+f.getName()+"\"");
		res.setContentLength((int)f.length());
		InputStream is =new FileInputStream(f);
		ServletOutputStream out=res.getOutputStream();
		IOUtils.copy(is,out);
		is.close();
		out.flush();
		out.close();
}
	//splitter download code

//splitter end


	
	
	
//extract data started
	
	@RequestMapping(value="/extract")
	public String extract() {
		return "index3";
	}
	@RequestMapping(value="/extractData",method=RequestMethod.POST)
	public String extractpdf(HttpServletRequest req,UserFile user)throws SQLException,IOException {
	
			
		String filepath=uploadFileOnServer2(user);
		req.setAttribute("filepath", filepath);
		return "extract";
	}	
	@RequestMapping(value="/extractpdf")
	private String extractor(String filepath, HttpServletRequest req) throws IOException {
		String password=req.getParameter("password");
		if(password==null)
		{
			File file=new File(filepath);
			PDDocument pd=null;
				try
					{
						pd=PDDocument.load(file);
					}
				catch(org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException e) 
					{
						req.setAttribute("filepath","/Users/shubhamkumar/Desktop/files/extract/11.pdf");
						return "requestPassword";
					}
			PDFTextStripper pdf=new PDFTextStripper();
			String s=pdf.getText(pd);
			req.setAttribute("data",s);
			System.out.println("extractor started");
			return "extractedData";
    	}
		
		else {
				File file = new File(req.getParameter("filePath"));
				PDDocument pd=null; 
					try{
						pd =PDDocument.load(file,password);
						}
					catch(org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException e) 
						{
							req.setAttribute("error", "password is not correct");
							return "requestPassword";			
						}
				PDFTextStripper pdf=new PDFTextStripper();
				String s=pdf.getText(pd);
				req.setAttribute("data", s);
				req.setAttribute("filepath","/Users/shubhamkumar/Desktop/files/extract/11.pdf");
				System.out.println(s);
				return "extractedData";
		
		}
	}
	

	private String uploadFileOnServer2(UserFile user) {
		
		String rootdirectory="/Users/shubhamkumar/Desktop/files/extract";
		File directory=new File(rootdirectory);
		if(!directory.exists())
			directory.mkdirs();
		MultipartFile f=user.getUserfile();
		String filepath=null;
		
		String filename=f.getOriginalFilename();
		
		if(filename!=null && filename.length()>0)
		{
			try {
				filepath="/Users/shubhamkumar/Desktop/files/extract/11.pdf";
				BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath));
				bos.write(f.getBytes());
				bos.close();
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}		
		System.out.println("pdf uploaded on server");	
		return filepath;
    }
	//error
	
//extractData end
//removePage start
	
	@RequestMapping(value="/remove")
	public String remove() {
		return "index4";
	}


	@RequestMapping(value="/removePage",method=RequestMethod.POST)
	public String removePage(HttpServletRequest req,UserFile user,HttpServletResponse res) throws IOException {
		String filepath=uploadFileOnServer3(user);
		
		req.setAttribute("file",filepath);
		
		
		return "removed";
	}
	
	@RequestMapping(value="/downloadremoved",method=RequestMethod.POST)
	public String removePage(HttpServletRequest req,HttpServletResponse res) throws IOException {
	
	String filepath=req.getParameter("filepath");
	System.out.println(filepath);
	File file=new File(filepath);
	
	PDDocument pd=PDDocument.load(file);
	
	int totalpage=pd.getNumberOfPages();
	System.out.println(totalpage);
	
	int i=Integer.parseInt(req.getParameter("ithpage"));
	
	pd.removePage(i);
	pd.save("/Users/shubhamkumar/Desktop/files/removepage/ram.pdf");
	pd.close();
	req.setAttribute("file","/Users/shubhamkumar/Desktop/files/removepage/ram.pdf");
	return "downloadremoved";
	}
	
	
	
	private String uploadFileOnServer3(UserFile user) {
		
		String rootdirectory="/Users/shubhamkumar/Desktop/files/removepage";
		File directory=new File(rootdirectory);
		if(!directory.exists())
			directory.mkdirs();
		MultipartFile f=user.getUserfile();
		String filepath=null;
		
		String filename=f.getOriginalFilename();
		
		if(filename!=null && filename.length()>0)
		{
			try {
				
				filepath=directory.getCanonicalPath()+File.separator+filename;
				BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath));
				bos.write(f.getBytes());
				bos.close();
				
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}		
		
		System.out.println("pdf uploaded on server");
		
		
		return filepath;
    }
	@RequestMapping(value="downloadnewremoved",method=RequestMethod.POST)
	public String downloadnewremoved(HttpServletRequest req,HttpServletResponse res) throws IOException {
	
	String mimetype=null;
	System.out.println(req.getParameter("filePath"));
	String file=req.getParameter("filePath");
	
	File f=new File(file);
	mimetype=getMimeType(f.getCanonicalPath());
	res.setContentType(mimetype);
	res.setHeader("content-Disposition", "attachment;filename=\""+f.getName()+"\"");
	res.setContentLength((int)f.length());
	
	InputStream is=new FileInputStream(f);

	ServletOutputStream out=res.getOutputStream();
	
	IOUtils.copy(is, out);
	out.flush();
	out.close();
	is.close();
	return "index";
	}


	
	  @RequestMapping(value="/pdfToImage")
	  public String pdfToImage() throws IOException
	  {
		File file=new File("/Users/shubhamkumar/Desktop/files/split2.pdf");
		PDDocument pd=PDDocument.load(file);
		int totalpage=pd.getNumberOfPages();
		PDFRenderer re=new PDFRenderer(pd);
		int i=0;
		while(i<totalpage) {
			
		BufferedImage img=re.renderImage(i);
		ImageIO.write(img,"JPEG", new File("/Users/shubhamkumar/Desktop/files/split2"+i+".jpg"));
		i++;
		
		}
		pd.close();
		return "index";
		  
	  }
	  
	  
	 @RequestMapping(value = "/protect")
	 public  String protect(HttpServletRequest req) throws IOException
	 {
		 File file=new File("/Users/shubhamkumar/Desktop/files/merge/raaaam1.pdf");
		 PDDocument pd=null;
		 try {
			 pd=PDDocument.load(file);
		 }
		 catch(org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException e) {
			 
			 req.setAttribute("filepath","");
			 return "requestPassword";
			 
		 }
		 AccessPermission ap=new AccessPermission();
		 
		 StandardProtectionPolicy policy=new StandardProtectionPolicy("code","parth",ap);
		 policy.setEncryptionKeyLength(256);
		 policy.setPermissions(ap);
		 pd.protect(policy);
		 pd.save("/Users/shubhamkumar/Desktop/files/merge/raaaam1.pdf");
		 pd.close();
		 System.out.println("encrypted");
		 return "index1";
	 }

	
	@RequestMapping(value="unlock",method=RequestMethod.POST)
	public String unlock() throws IOException {
		File file= new File("/Users/shubhamkumar/Desktop/files/merge/raaaam1.pdf");
		PDDocument pd=PDDocument.load(file,"code");
		pd.setAllSecurityToBeRemoved(true);
		pd.save("/Users/shubhamkumar/Desktop/files/merge/raaaam1.pdf");
		pd.close();
		return "index";
	}
//////////////////////////////////////////////////////////////////	

	
	
	
	  
	 
	//////////////////////////////////////////
	 
	 
	 @RequestMapping(value="/pdftohtml")
	 private String generateHTMLFromPDF() throws IOException, ParserConfigurationException {
		 String filename="/Users/shubhamkumar/Desktop/files/merge5.pdf";  
		 PDDocument pdf = PDDocument.load(new File(filename));
		    Writer output = new PrintWriter("/Users/shubhamkumar/Desktop/files/ram.html", "utf-8");
		    new PDFDomTree().writeText(pdf, output);		     
		    output.close();
		    return "index";
		}
	 
	 
	 
	 @RequestMapping(value="/htmltopdf")
	 private  String generatePDFFromHTML() throws DocumentException, IOException {
		 String filename="/Users/shubhamkumar/Desktop/udem2.html";
		 Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document,
		      new FileOutputStream("/Users/shubhamkumar/Desktop/files/ram.pdf"));
		    document.open();
		    XMLWorkerHelper.getInstance().parseXHtml(writer, document,
		      new FileInputStream(filename));
		    document.close();
		    return "index";
		}

	 

	 
	 @RequestMapping(value = "/imagetopdf")
	 public String imagetopdf() throws IOException {
		
		 PDDocument document = new PDDocument();
		 String someImage="/Users/shubhamkumar/Desktop/507809.png";
		InputStream in = new FileInputStream(someImage);
		 BufferedImage bimg = ImageIO.read(in);
		 float width = bimg.getWidth();
		 float height = bimg.getHeight();
		 PDPage page = new PDPage(new PDRectangle(width, height));
		 document.addPage(page); 
		 PDImageXObject img = PDImageXObject.createFromFile(someImage, document);
		 PDPageContentStream contentStream = new PDPageContentStream(document, page);
		 contentStream.drawImage(img, 0, 0);
		 contentStream.close();
		 in.close();
		 
		 document.save("/Users/shubhamkumar/Desktop/test.pdf");
		 document.close();
		 return "index";
	 }
	 
	 
	 ///////////////////////////////////////
	
	 
	 @RequestMapping(value ="/feedback")
	 public String feedback(UserFeed userfeed,HttpServletRequest req) throws SQLException
	 {
		 String name=req.getParameter("name");
		 String email=req.getParameter("email");
		 String feedback=req.getParameter("feedback");
		 System.out.println(name+email+feedback);
		 
		 homeService.insertInfo(userfeed);
		 ArrayList<UserFeed> l= new ArrayList<UserFeed>();
			 UserFeed u=new UserFeed();
			 
			 u.setName(name); 
			 u.setFeedback(feedback);;
			 l.add(u);
			 
		// req.setAttribute("xyz",l); 
		 System.out.println(l);
		 return "index";
	 }
	 
	 
	 

//login		 	
	 @RequestMapping(value="/loginpage")
	 public String loginpage( HttpSession session)
	 {
		 String id=(String) session.getAttribute("id");
		 if(id==null)
		 return "index9";
		 else
			 return "index";
	 }
	 
	 @RequestMapping(value="/loginverify",method=RequestMethod.POST)
		public String login(User user,HttpServletRequest req, HttpSession session) throws IOException, SQLException {
			System.out.println(session);
		
			
			String email=req.getParameter("username");
			String pwd=req.getParameter("password");
			HashMap<String, String> hm=homeService.getPassword();
			System.out.println(hm);
			
			
			if(hm.containsKey(email) && hm.get(email).equals(pwd)) {
				
				String x=email;
				 
				ArrayList<User> al=homeService.getProfile(x);
				User id=al.get(0);
				session.setAttribute("id", id);
				session.setAttribute("email", email);
				
				
				
				return "index";
			}
				else
				return "index9";					
	 
	 }
	 

	 
//logout
	 
	 @RequestMapping(value="/logoutpage")
	 public String logout(HttpSession session)
	 {
		 
		 session.invalidate();
		 
		 
		 return "index";
	 }
		
//profile
	 @RequestMapping(value="/profile")
	 public String profile(User user,HttpServletRequest req, HttpSession session)
	 {
		 String x1=(String) session.getAttribute("email");
		 
		 ArrayList<User> al=homeService.getProfile(x1);
		 
		 System.out.println(al.get(0));
		 
		 session.setAttribute("random",al.get(1));
		 req.setAttribute("email", x1);
		 return "profilepage";
	 }
	 
	 
	 
	 @RequestMapping(value = "/signup")
	 public String signup()
	 {		 
		 return "signupPage";
	 }
	 
	 
	 @RequestMapping(value = "emailvalidate",method = RequestMethod.POST)
	 public String emailvalidate(HttpServletRequest req,HttpSession session) throws AddressException, MessagingException
	 {
		 
		 Random rand=new Random();
			int rand_int = rand.nextInt(1000);
			
			String content=10+""+rand_int;
			session.setAttribute("content", content);
			session.setMaxInactiveInterval(120);
		String to=req.getParameter("emailId");
		 
		sendMail ss=new sendMail();
		return ss.emailUnlock(to,"Shukorates@gmail.com", content);
		}
	 
	 
	 @RequestMapping(value="otpvalidate",method=RequestMethod.POST)
	 public String validemail(HttpSession session,HttpServletRequest req)
	 {
		 String mainotp=req.getParameter("mainotp");
		 String otp=req.getParameter("otp");
		 System.out.println(mainotp);
		 System.out.println(otp);
		 
		 
		 if(mainotp.equals(otp))
		    {return "validotp";}		 
		 else
		 return "invalidotp";
		 
					 
	 }

	 
	 
	 @RequestMapping(value = "signupverify" ,method = RequestMethod.GET)
	 public String signupVerify(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	 { 
		 return "signupPage2";
	 }
	 
	 @RequestMapping(value = "finalsignup" ,method = RequestMethod.POST)
	 public String finalsignup(HttpServletRequest req,User user,HttpSession session) throws SQLException
	 {
		 Random rand=new Random();
			int rand_int = rand.nextInt(1000);
	 String email=req.getParameter("emailId");
	 String uname=req.getParameter("userName");
	 String mobile=req.getParameter("mobile");
	 String pwd=req.getParameter("password");
	 String uId=""+rand_int;
	 User u=new User();
	u.setEmailId(email);
	u.setUserName(uname);
	u.setMobile(mobile);
	u.setPassword(pwd);
	u.setUserId(uId);
	homeService.insertInfo(u);
	
	return "youaresignedup";
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 




}
