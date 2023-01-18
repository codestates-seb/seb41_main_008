package com.nfteam.server.transaction.controller;

import com.nfteam.server.dto.request.transaction.TransActionCreateRequest;
import com.nfteam.server.security.userdetails.MemberDetails;
import com.nfteam.server.transaction.service.TransActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trans")
public class TransActionController {

    private final TransActionService transActionService;

    @PostMapping
    public ResponseEntity<Void> buy(@RequestBody @Valid TransActionCreateRequest transActionCreateRequest,
                                    @AuthenticationPrincipal MemberDetails memberDetails) throws Exception {
        transActionService.saveOnePurchaseRecord(transActionCreateRequest, memberDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> bulkBuy(@RequestBody @Valid List<TransActionCreateRequest> transActionCreateRequestList,
                                        @AuthenticationPrincipal MemberDetails memberDetails) throws Exception {
        transActionService.saveBulkPurchaseRecord(transActionCreateRequestList, memberDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}