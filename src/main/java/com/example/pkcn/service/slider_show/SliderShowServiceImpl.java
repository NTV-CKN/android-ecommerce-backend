package com.example.pkcn.service.slider_show;

import com.example.pkcn.dto.response.SliderShowDTO;
import com.example.pkcn.entity.SliderShow;
import com.example.pkcn.repository.slider_show.ISliderShowRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SliderShowServiceImpl implements ISliderShowService {

    ISliderShowRepository repository;

    public SliderShowServiceImpl(ISliderShowRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SliderShowDTO> getSliderShow() {
        List<SliderShowDTO> dtoList = new ArrayList<>();
        List<SliderShow> list = repository.getSliderShow();
        for(SliderShow s : list) {
            SliderShowDTO dto = new SliderShowDTO();
            dto.setUrlImage(s.getUrlImage());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
