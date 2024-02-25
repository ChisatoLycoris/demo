# demo
使用 okhttp3 建立 帶憑證的請求 範例程式碼

參考以下

- 基礎知識: [什麼是 SSL/TLS 憑證](https://aws.amazon.com/tw/what-is/ssl-certificate/)
- 憑證轉換: [OpenSSL格式轉換](https://blog.miniasp.com/post/2019/04/17/Convert-PFX-and-CER-format-using-OpenSSL?fbclid=IwAR0tudcWTLuF_A6_lvIGXqubd9E6M6o88fW4zHEMGyzIX7SNcjhLI6vcWQk)
    - 憑證.pem檔，跟這篇中說的.cer檔是相同的東西
    - .pem檔有可能是公私鑰，也有可能是憑證，可以看檔案內容區分
      - 憑證: 文檔內容開頭 -----BEGIN CERTIFICATE-----
      - 私鑰: 文檔內容開頭 -----BEGIN RSA PRIVATE KEY-----

