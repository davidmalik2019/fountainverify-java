package com.fountainverify.fountainverify.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fountainverify.fountainverify.service.QRCodeService;

@RestController
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> generateQRCode(
    		@RequestParam("text") String text) throws Exception {

        byte[] qrCode = qrCodeService.generateQRCode(
                text,
                300,
                300
        );

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode);
    }
}