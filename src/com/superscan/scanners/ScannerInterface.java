package com.superscan.scanners;

interface ScannerInterface {
    <T> T transitionFunction(Character curr);
    <T> T tokenType();
    <T> T invalidToken();
    void resetStates(String token);

}
