package org.example.currency_exchanger.currency;

import static org.example.currency_exchanger.commons.Utils.convertUnicodeSignToSymbol;

public class Currency {
    private Long id;
    private String name;
    private String code;
    private String sign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = convertUnicodeSignToSymbol(sign);
    }

}
