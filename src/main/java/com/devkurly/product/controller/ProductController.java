package com.devkurly.product.controller;

import com.devkurly.product.domain.Paging;
import com.devkurly.product.domain.ProductDto;

import com.devkurly.product.service.ProductService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService; //필드주입 -> 생성자 주입 변경.


    @PostMapping("/write")
    public String write(ProductService productService, ProductDto productDto, Model m , HttpSession session, RedirectAttributes rattr) {
        String in_user = (String)session.getAttribute("id");
        productDto.setIn_user(in_user);

        try {
            int rowCnt = productService.write(productDto); // insert

            if(rowCnt!=1)
                throw new Exception("Write failed");

            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/product/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(productDto);
            m.addAttribute("msg", "WRT_ERR");
            return "product/product";
        }
    }
    @GetMapping("/write")
    public String write(Model m) {
        m.addAttribute("mode", "new");
        return "product/product"; // 읽기와 쓰기에 사용. 쓰기에 사용할떄는 mode=new
    }

    @PostMapping("/remove")
    public String remove(ProductService productService,Integer pdt_id, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String in_user = (String) session.getAttribute("id");
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            int rowCnt = productService.remove(pdt_id);
            if (rowCnt == 1)
                throw new Exception("product remove error");
            rattr.addFlashAttribute("msg", "DEL_OK");
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }
        return "redirect:/product/list?page=1&pageSize=10";
    }



    @GetMapping("/read")
    public String read(ProductService productService,Integer pdt_id, Integer page, Integer pageSize, Model m) {
        try {
              ProductDto productDto = productService.read(pdt_id);
                m.addAttribute("ProductDto",productDto);
              m.addAttribute("page",page);
              m.addAttribute("pagesize",pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return "product/product";
        }


    @GetMapping("/list")
    public String list(Integer page, Integer pageSize,
                       Model m, HttpServletRequest request,
                       HttpSession session, String order_sc) throws Exception{

        if(page==null) page=10;
        if(pageSize==null) pageSize=10;


       int totalCnt = productService.getCount();
        Paging ph = new Paging(totalCnt,page,pageSize);
        Map map = new HashMap();
        map.put("offset", (page-1)*pageSize);
        map.put("pageSize",pageSize);
        if(order_sc==null || order_sc == ""){
            List<ProductDto> list = productService.ProductList(map);
            m.addAttribute("list", list);
            m.addAttribute("ph",ph);
            return "product/productlist";
        }else{
            map.put("offset", (page-1)*pageSize);
            map.put("pageSize",pageSize);
            map.put("order_sc",order_sc);
            List<ProductDto> list = productService.ProductListDESC(map);
            m.addAttribute("list",list);
            m.addAttribute("ph",ph);
            return "product/productlist";

        }

    }



    @GetMapping("/newlist")
    public String newlist(ProductService productService, Integer page, Integer pageSize, Model m, HttpServletRequest request) {

        if(page==null) page=10;
        if(pageSize==null) pageSize=1000;

        try {
            int totalCnt = productService.getCount();
            Map map = new HashMap();
            map.put("pageSize", pageSize);

            List<ProductDto> list = productService.ProductNewList(map);
            m.addAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productNewlist";
    }

    @GetMapping("/bestlist")
    public String bestlist(ProductService productService, Integer page, Integer pageSize, Model m, HttpServletRequest request) {

        if(page==null) page=10;
        if(pageSize==null) pageSize=1000;

        try {
            int totalCnt = productService.getCount();
            Map map = new HashMap();
            map.put("pageSize", pageSize);

            List<ProductDto> list = productService.ProductBestList(map);
            m.addAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productBestlist";
    }



    @GetMapping("/thriftylist")
    public String thriftylist(ProductService productService,Integer page, Integer pageSize, Model m, HttpServletRequest request) {

        if(page==null) page=10;
        if(pageSize==null) pageSize=1000;

        try {
            int totalCnt = productService.getCount();
            Map map = new HashMap();
            map.put("pageSize", pageSize);

            List<ProductDto> list = productService.ProductThriftyList(map);
            m.addAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productThriftylist";
    }

    @PostMapping("/modify")
    public String modify(ProductService productService,ProductDto productDto, Model m , HttpSession session, RedirectAttributes rattr) {
        String in_user = (String)session.getAttribute("id");
        productDto.setIn_user(in_user);

        try {
            int rowCnt = productService.modify(productDto); // insert

            if(rowCnt!=1)
                throw new Exception("modify failed");

            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/product/list?page=1&pageSize=10";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(productDto);
            m.addAttribute("msg", "MOD_ERR");
            return "product/product";
        }

    }
}
