package com.buying.back.application.product.service;

import com.buying.back.application.product.controller.dto.OptionDto;
import com.buying.back.application.product.domain.Option;
import com.buying.back.application.product.domain.Product;
import com.buying.back.application.product.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductOptionHelperService {
    private final OptionService optionService;

    public Option createOption(Product product, OptionDto.Create optionDto) {
        return optionService.create(product, optionDto);
    }

    public List<Option> createOptionAll(Product product, List<OptionDto.Create> optionsDto) {
        return optionService.createAll(product, optionsDto);
    }

    public void deleteOptionByProduct(Product product) {
        optionService.deleteByProduct(product);
    }
}
