package com.superscan.scanners;

interface ScannerInterface {

    StringScanner.Tokens scan(String token, Integer start);

    /**
     * If an token is rejected, we need to know where it broke down.
     * Read Ass3 Incorrect Input section for details on how to report this.
     *
     * @return Index where a token was rejected.
     */
    Integer errorLocation();

}
