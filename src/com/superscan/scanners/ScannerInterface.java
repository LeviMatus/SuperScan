package com.superscan.scanners;

import com.superscan.enums.Tokens;

import java.util.ArrayList;

interface ScannerInterface {

    Tokens scan(ArrayList<Character> chars);

    /**
     * If an token is rejected, we need to know where it broke down.
     * Read Ass3 Incorrect Input section for details on how to report this.
     *
     * @return Index where a token was rejected.
     */
    Integer errorLocation();

}
