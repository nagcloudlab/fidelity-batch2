package com.example.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/transfer")
public class TransferController {

    // GET /transfer  -> show transfer form
    @RequestMapping(method = RequestMethod.GET)
    public String showTransferForm() {
        //... add any necessary model attributes here ...
        return "transfer-form"; // return the name of the view (e.g., transfer-form.html)
    }

    // POST /transfer -> process transfer form

}
