package com.example.pkcn.controller.slider_show;

import com.example.pkcn.dto.response.SliderShowDTO;
import com.example.pkcn.service.slider_show.ISliderShowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slider-show")
public class SliderShowController {

    ISliderShowService sliderShowService;

    public SliderShowController(ISliderShowService sliderShowService) {
        this.sliderShowService = sliderShowService;
    }

    @GetMapping
    public List<SliderShowDTO> getSliderShow() {
        return sliderShowService.getSliderShow();
    }

}
