package com.yourcompany.menus.domain.service;

import com.yourcompany.menus.domain.entity.Menu;

import java.math.BigDecimal;

public interface IMenuMetier {

    void validerMenu(Menu menu);

    BigDecimal calculerPrixTotal(Menu menu);
}

