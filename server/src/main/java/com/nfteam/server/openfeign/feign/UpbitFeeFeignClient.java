package com.nfteam.server.openfeign.feign;

import com.nfteam.server.openfeign.model.UpbitCoinPrice;
import com.nfteam.server.openfeign.model.UpbitWithdrawalFee;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "upbitFee", url = "https://api-manager.upbit.com/api/v1/kv")
public interface UpbitFeeFeignClient {
    @GetMapping("/UPBIT_PC_COIN_DEPOSIT_AND_WITHDRAW_GUIDE")
    UpbitWithdrawalFee getWithdrawalFee();
}
