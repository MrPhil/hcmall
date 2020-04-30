package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author rzhstart
 * @create 2020 - 03 - 28 - 10:58
 */

@FeignClient(value = "pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
