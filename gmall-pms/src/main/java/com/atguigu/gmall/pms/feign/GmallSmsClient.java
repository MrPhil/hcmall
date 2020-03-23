package com.atguigu.gmall.pms.feign;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.api.GmallSmsApi;
import com.atguigu.gmall.sms.vo.SkuSaleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author rzhstart
 * @create 2020 - 03 - 22 - 15:26
 */

@FeignClient(value = "sms-service"/*, path = "/sms/skubounds", url = "http://localhost:8888"*/)
public interface GmallSmsClient extends GmallSmsApi {

 /*   @PostMapping("/sku/sale/save")
    public Resp<Object> saveSale(@RequestBody SkuSaleVO skuSaleVO);*/
}
