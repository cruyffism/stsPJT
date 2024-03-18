package com.office.library.book.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.office.library.admin.member.AdminMemberVo;
import com.office.library.book.BookVo;
import com.office.library.book.HopeBookVo;
import com.office.library.book.RentalBookVo;
import com.office.library.book.admin.util.UploadFileService;

@Controller
//@Controller("admin.BookController")
@RequestMapping("/book/admin")
public class BookController {

	@Autowired
	BookService bookService; // BookService 빈 객체를 자동 주입

	@Autowired
	UploadFileService uploadFileService; // UploadFileService 빈 객체를 자동 주입

	// 도서 등록
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[BookController] registerBookForm()");

		String nextPage = "admin/book/register_book_form";

		return nextPage;
	}

	// 도서 등록 확인
	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVo bookVo, @RequestParam("file") MultipartFile file) { // 클라 >> 서버로 파일을 전송위해
																									// MultipartFile을 이용
		System.out.println("[BookController] registerBookConfirm()");

		String nextPage = "admin/book/register_book_ok";

		// 파일 저장
		String savedFileName = uploadFileService.upload(file);

		if (savedFileName != null) { // 신규 도서 추가
			bookVo.setB_thumbnail(savedFileName);
			int result = bookService.registerBookConfirm(bookVo);

			if (result <= 0)
				nextPage = "admin/book/register_book_ng";
		} else {
			nextPage = "admin/book/register_book_ok";
		}

		return nextPage;
	}

	// 도서 검색
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVo bookVo, Model model) {
		System.out.println("[UserBookController] searchBookConfirm()");

		String nextPage = "admin/book/search_book"; // 클라이언트 응답 시 search_book.jsp로이동

		List<BookVo> bookVos = bookService.searchBookConfirm(bookVo);

		model.addAttribute("bookVos", bookVos); // model에 bookVos를 담아서 뷰에 전달

		return nextPage; // 클라이언트 응답 시 search_book.jsp로이동
	}

	// 도서 상세
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] bookDetail()");

		String nextPage = "admin/book/book_detail";

		BookVo bookVo = bookService.bookDetail(b_no);

		model.addAttribute("bookVo", bookVo);

		return nextPage;
	}

	// 도서 수정
	@GetMapping("/modifyBookForm")
	public String modifyBookForm(@RequestParam("b_no") int b_no, Model model, HttpSession session) {
		System.out.println("[BookController] modifyBookForm()");

		String nextPage = "admin/book/modify_book_form";

		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
		if (loginedAdminMemberVo == null)
			return "redirect:/admin/member//loginForm";

		BookVo bookVo = bookService.bookDetail(b_no);

		model.addAttribute("bookVo", bookVo);

		return nextPage;
	}

	// 도서 수정 확인
	@PostMapping("/modifyBookConfirm")
	public String modifyBookConfirm(BookVo bookVo, @RequestParam("file") MultipartFile file, HttpSession session) {
		System.out.println("[BookController] modifyBookConfirm()");

		String nextPage = "admin/book/modify_book_ok";

		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
		if (loginedAdminMemberVo == null) // 로그인 안 되어 있으면 로그인폼으로 리다이렉트!
			return "redirect:/admin/member//loginForm";

		if (!file.getOriginalFilename().equals("")) { // 관리자가 표지 이미지를 변경했느지 체크하는 if문으로 만약 첨부한 파일의 이름이 존재한다면 새로운 파일이 저장됨!
			// SAVE FILE
			String savedFileName = uploadFileService.upload(file);
			if (savedFileName != null)
				bookVo.setB_thumbnail(savedFileName);

		}

		int result = bookService.modifyBookConfirm(bookVo);

		if (result <= 0)
			nextPage = "admin/book/modify_book_ng";

		return nextPage;

	}

	// 도서 삭제 확인
	@GetMapping("/deleteBookConfirm")
	public String deleteBookConfirm(@RequestParam("b_no") int b_no) {
		System.out.println("[BookController] deleteBookConfirm()");

		String nextPage = "admin/book/delete_book_ok";

		int result = bookService.deleteBookConfirm(b_no);

		if (result <= 0) {
			nextPage = "admin/book/delete_book_ng";

		}
		return nextPage;
	}

	// 대출 도서 목록
	@GetMapping("/getRentalBooks")
	public String getRentalBooks(Model model) {
		System.out.println("[BookController] getRentalBooks()");

		String nextPage = "admin/book/rental_books";

		List<RentalBookVo> rentalBookVos = bookService.getRentalBooks();

		model.addAttribute("rentalBookVos", rentalBookVos);

		return nextPage;

	}

	// 도서 반납 확인
	@GetMapping("/returnBookConfirm")
	public String returnBookConfirm(@RequestParam("b_no") int b_no, @RequestParam("rb_no") int rb_no) {
		System.out.println("[BookController] returnBookConfirm()");

		String nextPage = "admin/book/return_book_ok";

		int result = bookService.returnBookConfirm(b_no, rb_no);

		if (result <= 0)
			nextPage = "admin/book/return_book_ng";

		return nextPage;

	}

	// 희망 도서 목록
	@GetMapping("/getHopeBooks")
	public String getHopeBooks(Model model) {
		System.out.println("[BookController] getHopeBooks()");

		String nextPage = "admin/book/hope_books";

		List<HopeBookVo> hopeBookVos = bookService.getHopeBooks();

		model.addAttribute("hopeBookVos", hopeBookVos);

		return nextPage;

	}
	
	//희망 도서 등록(입고 처리)
	@GetMapping("/registerHopeBookForm")
	public String registerHopeBookForm(Model model, HopeBookVo hopeBookVo) {
		System.out.println("[BookController] registerHopeBookForm()");
		
		String nextPage = "admin/book/register_hope_book_form";
		
		model.addAttribute("hopeBookVo", hopeBookVo);
		
		return nextPage;
		
	}
	
	//희망 도서 등록(입고 처리) 확인
	@PostMapping("/registerHopeBookConfirm")
	public String registerHopeBookConfirm(BookVo bookVo, 
										  @RequestParam("hb_no") int hb_no, 
										  @RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] registerHopeBookConfirm()");
		
		System.out.println("hb_no: " + hb_no);
		
		String nextPage = "admin/book/register_book_ok";
		
		// SAVE FILE
		String savedFileName = uploadFileService.upload(file);
		
		if (savedFileName != null) {
			bookVo.setB_thumbnail(savedFileName);
			int result = bookService.registerHopeBookConfirm(bookVo, hb_no);
			
			if (result <= 0)
				nextPage = "admin/book/register_book_ng";
		
		} else {
			nextPage = "admin/book/register_book_ng";
		
		}
		
		return nextPage;

	}
	
	//전체 도서 목록
	@GetMapping("/getAllBooks")
	public String getAllBooks(Model model) {
		System.out.println("[BookController] getAllBooks()");
		
		String nextPage = "admin/book/full_list_of_books";
		
		List<BookVo> bookVos = bookService.getAllBooks();
		
		model.addAttribute("bookVos", bookVos);
		
		return nextPage;
		
	}
	
}
