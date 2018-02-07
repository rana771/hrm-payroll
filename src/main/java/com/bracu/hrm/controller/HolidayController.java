package com.bracu.hrm.controller;

import com.bracu.hrm.exception.WrongParameterException;
import com.bracu.hrm.model.util.Holiday;
import com.bracu.hrm.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@SessionAttributes("roles")
@RequestMapping("holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/create", "/new", "/view"}, method = RequestMethod.GET)
    public String create(Model model) {
        return "holiday/create";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody Holiday holiday, ModelMap modelMap, BindingResult result) throws WrongParameterException {
        if (result.hasErrors()) {
            throw new WrongParameterException(result.getAllErrors().toString());
//            return "holiday/create";
        } else {
            return holidayService.save(holiday);
//        return message;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") String id, ModelMap model) {
        String subGroupJson = holidayService.getHolidayById(Integer.parseInt(id));
//        System.err.println(subGroupJson);
        return subGroupJson;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Holiday holiday, BindingResult result, ModelMap modelMap) throws WrongParameterException {
        String message;
        if (result.hasErrors()) {
            throw new WrongParameterException(result.getAllErrors().toString());
//            return "holiday/create";
        } else {
//            if (!holidayService.uniqueNameCheck(holiday.getId(), holiday.getName())) {
//                message = messageSource.getMessage("non.unique.name", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
//                return message;
//            } else {
                return holidayService.update(holiday);

//            }

        }

    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(ModelMap modelMap) {
        String list = holidayService.getHolidayList();
//        System.err.println(list);
        return list;
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody Holiday holiday, BindingResult result, ModelMap modelMap) {
        String message;
        if (result.hasErrors()) {
            return "holiday/create";
        } else {
            if (!holidayService.uniqueNameCheck(holiday.getId(), holiday.getName())) {
                message = messageSource.getMessage("non.unique.name", new String[]{"Holiday Information", holiday.getName()}, Locale.getDefault());
                return message;
            } else {
                return holidayService.delete(holiday);

            }

        }

    }
}