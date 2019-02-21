package com.superscan.enums;

public enum States {
    STATE_1(Tokens.INDETERMINATE),
    STATE_2(Tokens.INDETERMINATE),
    STATE_3(Tokens.INDETERMINATE),
    STATE_4(Tokens.INDETERMINATE),
    STATE_5(Tokens.NUMBER),
    STATE_6(Tokens.INDETERMINATE),
    STATE_7(Tokens.NUMBER),
    STATE_8(Tokens.NUMBER),
    STATE_9(Tokens.INDETERMINATE),
    STATE_10(Tokens.NUMBER),
    STATE_11(Tokens.NUMBER),
    STATE_12(Tokens.NUMBER),
    STATE_13(Tokens.INDETERMINATE),
    STATE_14(Tokens.INDETERMINATE),
    STATE_15(Tokens.STRING),
    STATE_16(Tokens.INDETERMINATE),
    STATE_17(Tokens.INDETERMINATE),
    STATE_18(Tokens.INDETERMINATE),
    STATE_19(Tokens.INDETERMINATE),
    STATE_20(Tokens.INDETERMINATE),
    STATE_21(Tokens.BOOLEAN),
    STATE_22(Tokens.INDETERMINATE),
    STATE_23(Tokens.CHAR),
    STATE_24(Tokens.INDETERMINATE),
    STATE_25(Tokens.INDETERMINATE),
    STATE_26(Tokens.INDETERMINATE),
    STATE_27(Tokens.INDETERMINATE),
    STATE_28(Tokens.INDETERMINATE),
    STATE_29(Tokens.INDETERMINATE),
    STATE_30(Tokens.INDETERMINATE),
    STATE_31(Tokens.INDETERMINATE),
    STATE_32(Tokens.INDETERMINATE),
    STATE_33(Tokens.INDETERMINATE),
    STATE_34(Tokens.INDETERMINATE),
    STATE_35(Tokens.INDETERMINATE),
    STATE_36(Tokens.INDETERMINATE),
    STATE_37(Tokens.INDETERMINATE),
    STATE_38(Tokens.SINGLE_QOUTE),
    STATE_39(Tokens.OPENRD),
    STATE_40(Tokens.CLOSERD),
    STATE_41(Tokens.OPENSQ),
    STATE_42(Tokens.INDETERMINATE),
    STATE_43(Tokens.CLOSESQ),
    STATE_44(Tokens.OPENCU),
    STATE_45(Tokens.CLOSECU),
    STATE_46(Tokens.INDETERMINATE),
    STATE_47(Tokens.INDETERMINATE),
    STATE_48(Tokens.INDETERMINATE),
    STATE_49(Tokens.INDETERMINATE),
    STATE_50(Tokens.MULTI_QUOTE),
    STATE_51(Tokens.INDETERMINATE),
    STATE_52(Tokens.INDETERMINATE),
    STATE_53(Tokens.INDETERMINATE),
    STATE_54(Tokens.INDETERMINATE),
    STATE_55(Tokens.INDETERMINATE),
    STATE_56(Tokens.DEFINE),
    STATE_57(Tokens.INDETERMINATE),
    STATE_58(Tokens.INDETERMINATE),
    STATE_59(Tokens.INDETERMINATE),
    STATE_60(Tokens.INDETERMINATE),
    STATE_61(Tokens.BEGIN),
    STATE_62(Tokens.INDETERMINATE),
    STATE_63(Tokens.IF),
    STATE_64(Tokens.INDETERMINATE),
    STATE_65(Tokens.INDETERMINATE),
    STATE_66(Tokens.INDETERMINATE),
    STATE_67(Tokens.COND),
    STATE_68(Tokens.INDETERMINATE),
    STATE_69(Tokens.INDETERMINATE),
    STATE_70(Tokens.INDETERMINATE),
    STATE_71(Tokens.INDETERMINATE),
    STATE_72(Tokens.INDETERMINATE),
    STATE_73(Tokens.LET),
    STATE_74(Tokens.INDETERMINATE),
    STATE_75(Tokens.LAMBDA),
    STATE_76(Tokens.IDENTIFIER),
    STATE_77(Tokens.INDETERMINATE),
    STATE_78(Tokens.INDETERMINATE),
    STATE_79(Tokens.INDETERMINATE);

    private Tokens goalType;
    States(Tokens goalType) {this.goalType = goalType;}

}
