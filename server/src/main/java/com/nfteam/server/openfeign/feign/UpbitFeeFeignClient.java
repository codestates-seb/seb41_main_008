package com.nfteam.server.openfeign.feign;

import com.nfteam.server.openfeign.model.UpbitWithdrawalFee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "upbitFee", url = "https://api-manager.upbit.com/api/v1/kv")
public interface UpbitFeeFeignClient {

    @GetMapping("/UPBIT_PC_COIN_DEPOSIT_AND_WITHDRAW_GUIDE")
    UpbitWithdrawalFee getWithdrawalFee();

}
