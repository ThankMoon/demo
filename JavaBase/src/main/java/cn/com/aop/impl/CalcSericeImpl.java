package cn.com.aop.impl;

import cn.com.aop.CalcService;
import org.springframework.stereotype.Service;

@Service
public class CalcSericeImpl implements CalcService {

    @Override
    public int div(int x, int y) {
        int result = x / y;
        System.out.println("result:" + result);
        return result;
    }
}
