package com.superscan.scanners;

interface ScannerInterface {
    StringScanner.Tokens transitionFunction(Character curr);
    void resetStates(String token);

}
