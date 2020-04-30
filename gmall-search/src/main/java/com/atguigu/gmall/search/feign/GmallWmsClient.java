package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author rzhstart
 * @create 2020 - 03 - 28 - 10:58
 */

@FeignClient(value = "wms-service")
public interface GmallWmsClient extends GmallWmsApi {
}
