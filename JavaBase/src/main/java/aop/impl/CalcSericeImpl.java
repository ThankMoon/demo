package aop.impl;

import aop.CalcService;

public class CalcSericeImpl implements CalcService {

    @Override
    public int div(int x, int y) {
        int result = x / y;
        System.out.println("result:" + result);
        return result;
    }
}
