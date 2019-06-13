/*
 * Copyright (c)  2015-2019, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 2019/6/13 下午4:27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 */

package com.cpi.common.web.rest;

import com.cpi.common.service.CurrencyRateExtService;
import com.cpi.common.service.dto.CurrencyRateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing CurrencyRate.
 */
@RestController
@RequestMapping("/api")
public class CurrencyRateExtResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateExtResource.class);

    private static final String ENTITY_NAME = "cpicommonCurrencyRate";

    private final CurrencyRateExtService currencyRateExtService;

    public CurrencyRateExtResource(CurrencyRateExtService currencyRateExtService) {
        this.currencyRateExtService = currencyRateExtService;
    }


    @GetMapping("/currency-rates/get-last-rate/{currencyId}")
    public ResponseEntity<CurrencyRateDTO> getLastCurrencyRate(@PathVariable Long currencyId) {
        log.debug("REST request to get  Last CurrencyRate by currencyId: {}", currencyId);
        return ResponseEntity.ok().body(currencyRateExtService.findLastByCurrencyId(currencyId));
    }

}
