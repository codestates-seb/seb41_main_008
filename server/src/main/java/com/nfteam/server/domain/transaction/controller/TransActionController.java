package com.nfteam.server.domain.transaction.controller;

import com.nfteam.server.domain.transaction.service.TransActionService;
import com.nfteam.server.dto.request.transaction.TransActionCreateRequest;
import com.nfteam.server.dto.response.cart.CartResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trans")
public class TransActionController {

    private final TransActionService transActionService;

    public TransActionController(TransActionService transActionService) {
        this.transActionService = transActionService;
    }

    @PostMapping
    public ResponseEntity<CartResponse> buy(@RequestBody @Valid TransActionCreateRequest transActionCreateRequest,
                                            @AuthenticationPrincipal MemberDetails memberDetails) throws Exception {
        CartResponse cartResponse = transActionService.savePurchaseRecord(transActionCreateRequest, memberDetails);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

}